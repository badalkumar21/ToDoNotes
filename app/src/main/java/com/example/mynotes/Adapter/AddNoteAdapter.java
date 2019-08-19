package com.example.mynotes.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Entity.Note;
import com.example.mynotes.Entity.NoteTemp;
import com.example.mynotes.R;

import java.util.ArrayList;
import java.util.List;

public class AddNoteAdapter extends RecyclerView.Adapter<AddNoteAdapter.NoteViewHolder> {

    Context context;
    private List<NoteTemp> notes = new ArrayList<>();
    private OnItemClickListner listner;

    public AddNoteAdapter(Context context, List<NoteTemp> notes) {
        this.context = context;
        this.notes = notes;
    }

    public AddNoteAdapter(Context context) {
        this.context = context;
        Log.d("adapter_", "NoteAdapter: ");
    }

    public AddNoteAdapter() {
        Log.d("adapter_", "NoteAdapter: ");
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("adapter_", "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteTemp currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());

        String desc = currentNote.getDescription() + " \n" +
                currentNote.getDay() + " " + currentNote.getMonth() + " " + currentNote.getYear() + " \n" +
                currentNote.getDate() + " \n" +
                currentNote.getHr() + " " + currentNote.getMin() + " \n" +
                currentNote.getTime() + " \n";

        holder.textViewDescription.setText(desc);
//        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.textViewPriority.setText(currentNote.getDate());
        Log.d("adapter_", "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        Log.d("adapter_", "getItemCount: " + notes.size());
        return notes.size();
    }

    public void setNotes(List<NoteTemp> notes) {
        this.notes = notes;
        notifyDataSetChanged();
        Log.d("adapter_", "setNotes: " + notes.size());
    }

    public NoteTemp getNoteAt(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDescription = itemView.findViewById(R.id.textview_description);
            textViewPriority = itemView.findViewById(R.id.textview_priority);
            Log.d("adapter_", "NoteViewHolder: ");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(getNoteAt(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(NoteTemp note);
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }

}
