package com.example.mynotes.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Adapter.NoteAdapter;
import com.example.mynotes.Adapter.NoteAdapterNew;
import com.example.mynotes.DAO.NoteDao;
import com.example.mynotes.Entity.Note;
import com.example.mynotes.Model.NoteModel;
import com.example.mynotes.R;
import com.example.mynotes.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.mynotes.Activity.AddEditNoteActivity.EXTRA_DESC;
import static com.example.mynotes.Activity.AddEditNoteActivity.EXTRA_ID;
import static com.example.mynotes.Activity.AddEditNoteActivity.EXTRA_PRIORITY;
import static com.example.mynotes.Activity.AddEditNoteActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    public NoteViewModel noteViewModel;
    FloatingActionButton actionButton;
    NoteAdapterNew adapter;
    NoteAdapter adapterDetails;
    RecyclerView recyclerView;
    RecyclerView recyclerViewDetails;
    int scrollPosition = 0;
    List<NoteModel> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        actionButton = findViewById(R.id.button_add_note);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new NoteAdapterNew();
        recyclerView.setAdapter(adapter);

        recyclerViewDetails = findViewById(R.id.recycler_view_details);
        recyclerViewDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDetails.setHasFixedSize(true);
        adapterDetails = new NoteAdapter(this);
        recyclerViewDetails.setAdapter(adapterDetails);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int pos = getCurrentItem();

                    new ReadNotesByDateAsynTask().execute(noteList.get(pos).getDate());

                    Log.d("scroll_log_", "onScrollStateChanged: " + " State: " + newState + " pos: " + pos);
                }
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapterDetails.setNotes(notes);
                Log.d("adapter_", "onChanged: " + notes.size());
            }
        });

        noteViewModel.getAllNoteDate().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d("noteDate_", "onChanged: " + notes.size());
                new ReadSQLiteAsynTask().execute(notes);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
//                startActivityForResult(intent, ADD_NOTE_REQUEST);
                showCustomDialog();
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapterDetails.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerViewDetails);

        adapterDetails.setOnItemClickListner(new NoteAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(EXTRA_ID, note.getId());
                intent.putExtra(EXTRA_TITLE, note.getTitle());
                intent.putExtra(EXTRA_DESC, note.getDescription());
                intent.putExtra(EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_TITLE);
            String desc = data.getStringExtra(EXTRA_DESC);
            int priority = data.getIntExtra(EXTRA_PRIORITY, 0);

            Note note = new Note(title, desc, priority, 21, 6, 2019, "21 Jun 2019", 35, 11, "timeIOS");
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Failed to Uppdate", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EXTRA_TITLE);
            String desc = data.getStringExtra(EXTRA_DESC);
            int priority = data.getIntExtra(EXTRA_PRIORITY, 0);

            Note note = new Note(title, desc, priority, 21, 6, 2019, "21 Jun 2019", 35, 11, "timeIOS");
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " ", Toast.LENGTH_SHORT).show();
        }
    }

    private class ReadSQLiteAsynTask extends AsyncTask<List<Note>, Void, Void> {

        @Override
        protected Void doInBackground(List<Note>... lists) {

            List<NoteModel> noteModelList = new ArrayList<>();
            for (Note note : lists[0]) {

                String day = "Friday";
                String date = note.getDate();
                int priorityHigh = noteViewModel.getAllDateNotes_(note.getDate(), 1).size();
                int priorityMedium = noteViewModel.getAllDateNotes_(note.getDate(), 2).size();
                int priorityLow = noteViewModel.getAllDateNotes_(note.getDate(), 3).size();

                Log.d("noteDate_",
                        "Note: " + note.getDate() + "    Size: " + noteViewModel.getAllDateNotes(note.getDate()).size() +
                                "    High: " + noteViewModel.getAllDateNotes_(note.getDate(), 1).size() +
                                "    Medium: " + noteViewModel.getAllDateNotes_(note.getDate(), 2).size() +
                                "    Low: " + noteViewModel.getAllDateNotes_(note.getDate(), 3).size()
                );

                NoteModel noteModel = new NoteModel(day, date, priorityHigh, priorityMedium, priorityLow);
                noteModelList.add(noteModel);
            }
            setAdapter(noteModelList);
            return null;
        }
    }

    private class ReadNotesByDateAsynTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            List<Note> noteList = noteViewModel.getAllDateNotes(strings[0]);
            setRecyclerViewDetailsAdapter(noteList);
            return null;
        }
    }


    public void setAdapter(List<NoteModel> noteModelList) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                noteList = noteModelList;
                adapter.setNotes(noteModelList);
                new ReadNotesByDateAsynTask().execute(noteList.get(0).getDate());
            }
        }, 0);
    }

    public void setRecyclerViewDetailsAdapter(List<Note> noteList) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterDetails.setNotes(noteList);
            }
        }, 0);
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

