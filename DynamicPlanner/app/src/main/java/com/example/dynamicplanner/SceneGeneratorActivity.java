package com.example.dynamicplanner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dynamicplanner.databinding.ActivitySceneGeneratorBinding;

import java.util.ArrayList;
import java.util.Random;

public class SceneGeneratorActivity extends AppCompatActivity {

    ActivitySceneGeneratorBinding binding;
    private SceneAdapter sceneAdapter;
    private ArrayList<String> tasksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySceneGeneratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //hide soft keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvTaskList.setLayoutManager(layoutManager);
        sceneAdapter = new SceneAdapter(1,tasksList, this, null);
        binding.rvTaskList.setAdapter(sceneAdapter);

        binding.imgAddTask.setOnClickListener(v->{
            if(!TextUtils.isEmpty(binding.edtTaskValue.getText().toString())){
                tasksList.add(binding.edtTaskValue.getText().toString());
                sceneAdapter.notifyDataSetChanged();
                binding.edtTaskValue.setText("");
                Toast.makeText(this, "New Task Added!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnPickTask.setOnClickListener(v->{
            String getPickedStr = tasksList.get(new Random().nextInt(tasksList.size()));
            binding.txtoutput.setText(getPickedStr);
            sceneAdapter = new SceneAdapter(1, tasksList, this, getPickedStr);
            binding.rvTaskList.setAdapter(sceneAdapter);
        });

        binding.btnReset.setOnClickListener(v->{
            tasksList.clear();
            sceneAdapter.notifyDataSetChanged();
            binding.txtoutput.setText("");
            Toast.makeText(this, "All Task Cleared!", Toast.LENGTH_SHORT).show();
        });

        binding.imgBack5.setOnClickListener(v-> finish());
    }
}