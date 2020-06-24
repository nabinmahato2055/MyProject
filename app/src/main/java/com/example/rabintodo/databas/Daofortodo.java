package com.example.rabintodo.databas;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Daofortodo {

    @Insert
    void Insert(Note note);

    @Update
    void Update(Note note);

    @Delete
    void Delete(Note note);

    @Query("DELETE FROM nabin_table")
    void deleteAllNotes();


    @Query("SELECT * FROM nabin_table ORDER BY priority DESC")
    LiveData<List<Note>>getAllNotes();

    //will be notified immediately idf changes made
}
