package com.example.mynotes.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mynotes.DAO.NoteDao;
import com.example.mynotes.Entity.Note;
import com.example.mynotes.Entity.NoteTemp;

@Database(entities = {Note.class, NoteTemp.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database_")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCalback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCalback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynTask(instance).execute();
        }
    };

    private static class PopulateDbAsynTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public PopulateDbAsynTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            noteDao.insert(new Note("Title 1", "Desc 1", 1));
//            noteDao.insert(new Note("Title 2", "Desc 2", 2));
//            noteDao.insert(new Note("Title 3", "Desc 3", 3));
            return null;
        }
    }

}
