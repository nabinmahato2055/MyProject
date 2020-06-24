package com.example.rabintodo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rabintodo.databas.Daofortodo;
import com.example.rabintodo.databas.Note;
import com.example.rabintodo.databas.nabindatabase;

import java.util.List;

public class RepositoryNote {
    private Daofortodo daofortodo;
    private LiveData<List<Note>> allNotes;

    public RepositoryNote(Application application){
        nabindatabase nabindatabase = com.example.rabintodo.databas.nabindatabase.getInstance(application);
        daofortodo = nabindatabase.todoDao();
        allNotes = daofortodo.getAllNotes();

    }

    public void insert(Note note){
        new InsertNoteAsyncTask(daofortodo).execute(note);

    }

    public void update(Note note){
        new UpdateNoteAsyncTask(daofortodo).execute(note);

    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(daofortodo).execute(note);

    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(daofortodo).execute();

    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void,Void>{
        private Daofortodo daofortodo;

        private InsertNoteAsyncTask(Daofortodo daofortodo){
            this.daofortodo = daofortodo;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            daofortodo.Insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void,Void>{
        private Daofortodo daofortodo;

        private UpdateNoteAsyncTask(Daofortodo daofortodo){
            this.daofortodo = daofortodo;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            daofortodo.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void,Void>{
        private Daofortodo daofortodo;

        private DeleteNoteAsyncTask(Daofortodo daofortodo){
            this.daofortodo = daofortodo;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            daofortodo.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void,Void>{
        private Daofortodo daofortodo;

        private DeleteAllNoteAsyncTask(Daofortodo daofortodo){
            this.daofortodo = daofortodo;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            daofortodo.deleteAllNotes();
            return null;
        }
    }

}
