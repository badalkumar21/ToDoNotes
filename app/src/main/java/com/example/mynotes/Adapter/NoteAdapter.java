package com.example.mynotes.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Entity.Note;
import com.example.mynotes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    Context context;
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListner listner;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public NoteAdapter() {
        Log.d("adapter_", "NoteAdapter: ");
    }

    public NoteAdapter(Context context) {
        this.context = context;
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
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
//        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.textViewPriority.setText(currentNote.getDate());
        Log.d("adapter_", "onBindViewHolder: ");


        switch (currentNote.getPriority()) {
            case 1:
                holder.linearLayoutContainer.setBackground(context.getResources().getDrawable(R.color.colorOrange));
                break;

            case 2:
                holder.linearLayoutContainer.setBackground(context.getResources().getDrawable(R.color.colorYellow));
                break;

            case 3:
                holder.linearLayoutContainer.setBackground(context.getResources().getDrawable(R.color.colorGreen));
                break;
        }


    }

    @Override
    public int getItemCount() {
        Log.d("adapter_", "getItemCount: " + notes.size());
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
        Log.d("adapter_", "setNotes: " + notes.size());
    }

    public void setNotifyDataChanged() {
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private LinearLayout linearLayoutContainer;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDescription = itemView.findViewById(R.id.textview_description);
            textViewPriority = itemView.findViewById(R.id.textview_priority);
            linearLayoutContainer = itemView.findViewById(R.id.container);
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
        void onItemClick(Note note);
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }

}
