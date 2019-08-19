package com.example.mynotes.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import com.example.mynotes.DAO.NoteDao;
import com.example.mynotes.Entity.Note;
import com.example.mynotes.Entity.NoteTemp;
import com.example.mynotes.Room.NoteDatabase;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<NoteTemp>> allNotesTemp;
    private LiveData<List<Note>> noteDate;
    private List<Note> dateNotes;

    public NoteRepository(Application application) {

        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        allNotesTemp = noteDao.getAllNotesTemp();
        noteDate = noteDao.getAllNoteDate();


    }

    public void insert(Note note) {
        new InsertNoteAsynTask(noteDao).execute(note);
    }

    public void insert(NoteTemp note) {
        new InsertNoteAsynTaskTemp(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsynTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsynTask(noteDao).execute(note);
    }

    public void deleteTemp(NoteTemp note) {
        new DeleteNoteTempAsynTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsynTask(noteDao).execute();
    }

    public void deleteAllNotesTemp() {
        new DeleteAllNoteTempAsynTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteTemp>> getAllNotesTemp() {
        return allNotesTemp;
    }

    public LiveData<List<Note>> getAllNoteDate() {
        return noteDate;
    }

    public List<Note> getAllDateNotes(String date) {
        List<Note> list;
        list = noteDao.getAllDateNotes(date);
        return list;
    }

    public List<Note> getAllDateNotes_(String date, int priority) {
        return noteDao.getAllDateNotes_(date, priority);
    }


    private static class InsertNoteAsynTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public InsertNoteAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class InsertNoteAsynTaskTemp extends AsyncTask<NoteTemp, Void, Void> {

        private NoteDao noteDao;

        public InsertNoteAsynTaskTemp(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteTemp... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsynTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public UpdateNoteAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsynTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public DeleteNoteAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNoteAsynTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public DeleteAllNoteAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

    private static class DeleteAllNoteTempAsynTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public DeleteAllNoteTempAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotesTemp();
            return null;
        }
    }

    private static class DeleteNoteTempAsynTask extends AsyncTask<NoteTemp, Void, Void> {

        private NoteDao noteDao;

        public DeleteNoteTempAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteTemp... notes) {
            noteDao.deleteTemp(notes[0]);
            return null;
        }
    }


    private static class GetAllDateNotesAsynTask extends AsyncTask<String, List<Note>, List<Note>> {

        private NoteDao noteDao;

        List<Note> list;

        public GetAllDateNotesAsynTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected List<Note> doInBackground(String... strings) {
            list = noteDao.getAllDateNotes(strings[0]);
            return list;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
        }
    }

}
