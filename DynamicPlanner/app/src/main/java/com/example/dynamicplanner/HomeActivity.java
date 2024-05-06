package com.example.dynamicplanner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.Calendar.MainActivity;
import com.example.dynamicplanner.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgOptions.setOnClickListener(v -> {

            String[] colors = {"Update Password", "Logout"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select options");
            builder.setItems(colors, (dialog, which) -> {
                if(which == 0){
                    startActivity(new Intent(HomeActivity.this, ForgotPasswordActivity.class));
                }else{
                    new AlertDialog.Builder(HomeActivity.this)
                            .setMessage("Are you sure you want to Logout?")
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.yes), (dialog1, id) -> {
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            })
                            .setNegativeButton(getString(R.string.no), null)
                            .show();
                }
            });

            builder.show();

        });

        binding.btnNotepad.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NoteSaveActivity.class)));
        binding.btnTaskManager.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, TaskManagerActivity.class)));
        binding.btnCalendar.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MainActivity.class)));
        binding.btnSceneGenerator.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SceneGeneratorActivity.class)));
        binding.btnVoiceText.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, VoiceToTextActivity.class)));

    }
}