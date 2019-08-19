package com.example.mynotes.Adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Entity.Note;
import com.example.mynotes.Model.NoteModel;
import com.example.mynotes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapterNew extends RecyclerView.Adapter<NoteAdapterNew.NoteViewHolder> {

    Context context;
    private List<NoteModel> notes = new ArrayList<>();
    private OnItemClickListner listner;
    CountDownTimer timer;

    public NoteAdapterNew(Context context, List<NoteModel> notes) {
        this.context = context;
        this.notes = notes;
    }

    public NoteAdapterNew() {
        Log.d("adapter_", "NoteAdapter: ");
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("adapter_", "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteModel currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getDate());
        holder.textViewDescription.setText(currentNote.getDay());
        holder.textViewPriority.setText(currentNote.getDate());
        holder.textViewPriorityHigh.setText(String.valueOf(currentNote.getPriorityHigh()));
        holder.textViewPriorityMedium.setText(String.valueOf(currentNote.getPriorityMedium()));
        holder.textViewPriorityLow.setText(String.valueOf(currentNote.getPriorityLow()));
        setupTimer(holder.textViewTimer);
        Log.d("adapter_", "onBindViewHolder: ");
    }

    private void setupTimer(TextView textViewTimer) {
        timer = new CountDownTimer(10000000, 1000) {
            @Override
            public void onTick(long l) {
                long hr = ((l / 1000) / 60) / 60;
                long min = ((l / 1000) / 60) % 60;
                long sec = ((l / 1000) % 60);
                textViewTimer.setText(hr + " : " + min + " : " + sec);
            }

            @Override
            public void onFinish() {
                textViewTimer.setText("00 : 00 : 00");
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        Log.d("adapter_", "getItemCount: " + notes.size());
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setNotes(List<NoteModel> notes) {
        this.notes = notes;
        notifyDataSetChanged();
        Log.d("adapter_", "setNotes: " + notes.size());
    }

    public NoteModel getNoteAt(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewPriorityHigh;
        private TextView textViewPriorityMedium;
        private TextView textViewPriorityLow;
        private TextView textViewTimer;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDescription = itemView.findViewById(R.id.textview_description);
            textViewPriority = itemView.findViewById(R.id.textview_priority);
            textViewPriorityHigh = itemView.findViewById(R.id.textView4);
            textViewPriorityMedium = itemView.findViewById(R.id.textView5);
            textViewPriorityLow = itemView.findViewById(R.id.textView6);
            textViewTimer = itemView.findViewById(R.id.progressBar4);
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
        void onItemClick(NoteModel note);
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }

}
