package com.example.dynamicplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivityNoteSaveBinding;

import java.util.ArrayList;
import java.util.HashSet;

public class NoteSaveActivity extends AppCompatActivity {

    ActivityNoteSaveBinding binding;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteSaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {

            notes.add("Example note");
        } else {
            notes = new ArrayList(set);
        }

        // Using custom listView Provided by Android Studio
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);

        binding.listView.setAdapter(arrayAdapter);

        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {

            // Going from MainActivity to NotesEditorActivity
            Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
            intent.putExtra("noteId", i);
            startActivity(intent);

        });

        binding.listView.setOnItemLongClickListener((adapterView, view, i, l) -> {

            final int itemToDelete = i;
            // To delete the data from the App
            new AlertDialog.Builder(NoteSaveActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialogInterface, i1) -> {
                        notes.remove(itemToDelete);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                        HashSet<String> set1 = new HashSet(NoteSaveActivity.notes);
                        sharedPreferences1.edit().putStringSet("notes", set1).apply();
                    }).setNegativeButton("No", null).show();
            return true;
        });

        binding.imgBack2.setOnClickListener(v-> finish());
        binding.imgGotoEdit.setOnClickListener(v-> startActivity(new Intent(NoteSaveActivity.this, NoteEditActivity.class)));
        binding.txtAddNewNote.setOnClickListener(v-> startActivity(new Intent(NoteSaveActivity.this, NoteEditActivity.class)));
    }
}