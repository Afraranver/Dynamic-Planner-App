package com.example.dynamicplanner.Calendar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    EventCalendarDialog eventCalendarDialog;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack4.setOnClickListener(v-> finish());

    }
}