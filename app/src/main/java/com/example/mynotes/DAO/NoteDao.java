package com.example.mynotes.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mynotes.Entity.Note;
import com.example.mynotes.Entity.NoteTemp;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE  FROM note_table_")
    void deleteAllNotes();

    @Query("DELETE  FROM note_table_temp")
    void deleteAllNotesTemp();

    @Query("SELECT * FROM note_table_ ORDER BY time ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table_temp ORDER BY priority ASC")
    LiveData<List<NoteTemp>> getAllNotesTemp();

//    @Query("SELECT * FROM note_table_temp ORDER BY priority DESC")
//    List<NoteTemp> getAllNotesTemp_();

    @Insert
    void insert(NoteTemp note);

    @Delete
    void deleteTemp(NoteTemp note);

    @Query("SELECT * FROM note_table_ GROUP BY date")
    LiveData<List<Note>> getAllNoteDate();

    @Query("SELECT * FROM note_table_ WHERE date IN (:date)")
    List<Note> getAllDateNotes(String date);

    @Query("SELECT * FROM note_table_ WHERE date IN (:date) and PRIORITY = :priority")
    List<Note> getAllDateNotes_(String date, int priority);

}
