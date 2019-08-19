package com.example.mynotes.ViewModel;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mynotes.Entity.Note;
import com.example.mynotes.Entity.NoteTemp;
import com.example.mynotes.Repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<NoteTemp>> allNotesTemp;
    private LiveData<List<Note>> noteDate;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allNotesTemp = repository.getAllNotesTemp();
        noteDate = repository.getAllNoteDate();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void insert(NoteTemp note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }


    public void deleteTemp(NoteTemp note) {
        repository.deleteTemp(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public void deleteAllNotesTemp() {
        repository.deleteAllNotesTemp();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteTemp>> getAllNotesTemp() {
        return allNotesTemp;
    }

    public LiveData<List<NoteTemp>> getTimeCol() {
        return allNotesTemp;
    }

    public LiveData<List<Note>>  getAllNoteDate() {
        return noteDate;
    }

    public List<Note>  getAllDateNotes(String date) {
        return repository.getAllDateNotes(date);
    }

    public List<Note>  getAllDateNotes_(String date, int priority) {
        return repository.getAllDateNotes_(date, priority);
    }

}
