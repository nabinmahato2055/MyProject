package com.example.rabintodo.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rabintodo.NoteViewModel;
import com.example.rabintodo.R;
import com.example.rabintodo.databas.DatabaseHelper;
import com.example.rabintodo.databas.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final int ADD_REQUEST = 1;

    public static final int EDIT_TODO_REQUEST = 2;  //for edit

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TodoAddEditActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DatabaseHelper.AdapterForTodo adapter = new DatabaseHelper.AdapterForTodo();
        recyclerView.setAdapter(adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update Recyclerview
                adapter.setTodo(notes);
//                Toast.makeText(HomeActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HomeActivity.this, "Todo deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new DatabaseHelper.AdapterForTodo.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(HomeActivity.this, TodoAddEditActivity.class);
                intent.putExtra(TodoAddEditActivity.EXTRA_ID, note.getId());

                intent.putExtra(TodoAddEditActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(TodoAddEditActivity.EXTRA_DESCRIPTION, note.getTitle());
                intent.putExtra(TodoAddEditActivity.EXTRA_PRIORITY, note.getTitle());

                startActivityForResult(intent, EDIT_TODO_REQUEST);

            }


        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(TodoAddEditActivity.EXTRA_TITLE);
            String description = data.getStringExtra(TodoAddEditActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(TodoAddEditActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(TodoAddEditActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Todo cannot be updated ", Toast.LENGTH_SHORT).show();
                return;

            }
            String title = data.getStringExtra(TodoAddEditActivity.EXTRA_TITLE);
            String description = data.getStringExtra(TodoAddEditActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(TodoAddEditActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title,description,priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Todo Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_All_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Todo notes deleted", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.feedsend:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s = {"nabinmahato2055@gmail.com"};
                i.putExtra(Intent.EXTRA_EMAIL, s);
                i.putExtra(Intent.EXTRA_SUBJECT, "'Write your Subject'");
                i.putExtra(Intent.EXTRA_TEXT, "Your FeedBack");
                i.setType("message/rfc822");
                Intent chooser = Intent.createChooser(i, "Give your Todo Feedback from Gmail");
                startActivity(chooser);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
