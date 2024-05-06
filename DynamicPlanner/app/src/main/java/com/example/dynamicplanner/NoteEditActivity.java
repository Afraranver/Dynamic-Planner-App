package com.example.dynamicplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivityNoteEditBinding;

import java.util.HashSet;

public class NoteEditActivity extends AppCompatActivity {
    int noteId;
    ActivityNoteEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Fetch data that is passed from MainActivity
        Intent intent = getIntent();

        // Accessing the data using key and value
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            binding.editText.setText(NoteSaveActivity.notes.get(noteId));
        } else {

            NoteSaveActivity.notes.add("");
            noteId = NoteSaveActivity.notes.size() - 1;
            NoteSaveActivity.arrayAdapter.notifyDataSetChanged();

        }

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NoteSaveActivity.notes.set(noteId, String.valueOf(charSequence));
                NoteSaveActivity.arrayAdapter.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(NoteSaveActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });

        binding.imgBack3.setOnClickListener(v-> finish());

        //show keyboard
        binding.editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.editText, InputMethodManager.SHOW_IMPLICIT);
    }
}