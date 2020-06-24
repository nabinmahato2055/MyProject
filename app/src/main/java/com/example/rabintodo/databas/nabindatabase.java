package com.example.rabintodo.databas;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Note.class},version = 1)
public abstract class nabindatabase extends RoomDatabase {

    //need to turn this class into singleton
    private static nabindatabase instance;

    public abstract Daofortodo todoDao();

    public static synchronized nabindatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    nabindatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


   private static  class PopulateDbAsyncTask extends AsyncTask<Void,Void, Void>{

       private Daofortodo daofortodo;
       private PopulateDbAsyncTask(nabindatabase db){
           daofortodo = db.todoDao();
       }

       @Override
       protected Void doInBackground(Void... voids) {
          daofortodo.Insert(new Note("Title 1", "Description 1",1));
           return null;
       }
   }

}
