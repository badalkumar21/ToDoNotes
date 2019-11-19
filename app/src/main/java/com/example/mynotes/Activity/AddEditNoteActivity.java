package com.example.mynotes.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mynotes.Adapter.AddNoteAdapter;
import com.example.mynotes.Entity.Note;
import com.example.mynotes.Entity.NoteTemp;
import com.example.mynotes.Model.DateModel;
import com.example.mynotes.Model.TimeModel;
import com.example.mynotes.R;
import com.example.mynotes.ViewModel.NoteViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "package.ID";
    public static final String EXTRA_TITLE = "package.TITLE";
    public static final String EXTRA_DESC = "package.DESC";
    public static final String EXTRA_PRIORITY = "package.PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDesc;
    private NumberPicker numberPickerPriority;

    RecyclerView recyclerView;
    ImageButton imageButton;
    TimePickerDialog mTimePicker;

    private NoteViewModel noteViewModel;
    List<NoteTemp> list;

    TextView datePickerText;
    TextView timePickerText;
    TextView textViewAddDesc;
    TextView textViewHideDesc;
    TextView textViewPrirityHigh;
    TextView textViewPrirityMedium;
    TextView textViewPrirityLow;

    final Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd MMM yyyy";
    SimpleDateFormat sdf;

    DateFormat timeFormater = new SimpleDateFormat("HH:mm:ss");
    Date dateTime = new Date();

    int hr;
    int min;
    int day;
    int month;
    int year;
    int notePriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        notePriority = 3;
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDesc = findViewById(R.id.edit_text_desc);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        imageButton = findViewById(R.id.button_add_note);
        textViewAddDesc = findViewById(R.id.text_add_description);
        textViewHideDesc = findViewById(R.id.text_hide_description);
        textViewPrirityHigh = findViewById(R.id.priorityHigh);
        textViewPrirityMedium = findViewById(R.id.priorityMedium);
        textViewPrirityLow = findViewById(R.id.priorityLow);

        setupPriorityClickListner();

        textViewAddDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDesc.setVisibility(View.VISIBLE);
                textViewHideDesc.setVisibility(View.VISIBLE);
                textViewAddDesc.setVisibility(View.GONE);
            }
        });

        textViewHideDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDesc.setVisibility(View.GONE);
                textViewHideDesc.setVisibility(View.GONE);
                textViewAddDesc.setVisibility(View.VISIBLE);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_add);
        AddNoteAdapter adapter = new AddNoteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(3);

        sdf = new SimpleDateFormat(myFormat, Locale.US);

        hr = 00;
        min = 00;

        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        year = myCalendar.get(Calendar.YEAR);

        datePickerText = findViewById(R.id.date_picker);
        timePickerText = findViewById(R.id.time_picket);

        datePickerText.setText(sdf.format(myCalendar.getTime()));
        timePickerText.setText("- - : - -");

        datePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditNoteActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePicker = new TimePickerDialog(AddEditNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timePickerText.setText(selectedHour + ":" + selectedMinute);
                        hr = selectedHour;
                        min = selectedMinute;
                    }
                }, hr, min, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotesTemp().observe(this, new Observer<List<NoteTemp>>() {
            @Override
            public void onChanged(List<NoteTemp> notes) {
                adapter.setNotes(notes);
                list = notes;
                Log.d("adapter_temp", "onChanged: " + notes.size());
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDesc.setText(intent.getStringExtra(EXTRA_DESC));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoTempDB();
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
                noteViewModel.deleteTemp(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setupPriorityClickListner() {
        textViewPrirityHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notePriority = 1;
                textViewPrirityHigh.setBackground(getResources().getDrawable(R.drawable.button_bg_orange));
                textViewPrirityMedium.setBackground(getResources().getDrawable(R.drawable.button_bg_gray));
                textViewPrirityLow.setBackground(getResources().getDrawable(R.drawable.button_bg_gray));
            }
        });

        textViewPrirityMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notePriority = 2;
                textViewPrirityHigh.setBackground(getResources().getDrawable(R.drawable.button_bg_gray));
                textViewPrirityMedium.setBackground(getResources().getDrawable(R.drawable.button_bg_yellow));
                textViewPrirityLow.setBackground(getResources().getDrawable(R.drawable.button_bg_gray));
            }
        });

        textViewPrirityLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notePriority = 3;
                textViewPrirityHigh.setBackground(getResources().getDrawable(R.drawable.button_bg_gray));
                textViewPrirityMedium.setBackground(getResources().getDrawable(R.drawable.button_bg_gray));
                textViewPrirityLow.setBackground(getResources().getDrawable(R.drawable.button_bg_green));
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int yr, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, yr);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            datePickerText.setText(sdf.format(myCalendar.getTime()));

            day = dayOfMonth;
            month = monthOfYear;
            year = yr;
        }
    };


    private void addtoTempDB() {
        String title = editTextTitle.getText().toString();
        String desc = editTextDesc.getText().toString();
        int priority = notePriority;
        int hour = hr;
        int minute = min;


        editTextTitle.setText("");
        editTextDesc.setText("");
        timePickerText.setText("- - : - -");
        hr = 00;
        min = 00;
        numberPickerPriority.setValue(1);

        NoteTemp note = new NoteTemp(title, desc, priority, day, month, year, sdf.format(myCalendar.getTime()), minute, hour, getTimeISO());
        noteViewModel.insert(note);
    }

    private String getTimeISO() {
        return year + "-" + month + "-" + day + "-" + hr + "-" + min + "-" + "00.000";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String desc = editTextDesc.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (list.size() > 0) {
            for (NoteTemp noteTemp : list) {
                Note note = new Note(
                        noteTemp.getTitle(),
                        noteTemp.getDescription(),
                        noteTemp.getPriority(),
                        noteTemp.getDay(),
                        noteTemp.getMonth(),
                        noteTemp.getYear(),
                        noteTemp.getDate(),
                        noteTemp.getMin(),
                        noteTemp.getHr(),
                        noteTemp.getTime()
                );

                noteViewModel.insert(note);
            }
        }
        noteViewModel.deleteAllNotesTemp();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "You cannot leave any field empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESC, desc);
        intent.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        Log.d("extra_id_log", "ID: " + id);

        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);

        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteViewModel.deleteAllNotesTemp();
    }

    public String getTime() {
        return timeFormater.format(dateTime);
    }
}
