package com.example.dynamicplanner;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivityTaskManagerBinding;

import java.util.ArrayList;

public class TaskManagerActivity extends AppCompatActivity {

    ActivityTaskManagerBinding binding;
    DBHelper dbHelper;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);
        ArrayList<String> array_list = dbHelper.getAllData();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        binding.listView.setAdapter(arrayAdapter);

        binding.listView.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            // TODO Auto-generated method stub
            int id_To_Search = arg2 + 1;

            AlertDialog.Builder builder = new AlertDialog.Builder(TaskManagerActivity.this);
            builder.setMessage(R.string.areyousure)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        dbHelper.deleteContact(id_To_Search);
                        Toast.makeText(getApplicationContext(), getString(R.string.deletedsuccess),
                                Toast.LENGTH_SHORT).show();
                        ArrayList<String> array_list1 = dbHelper.getAllData();
                        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list1);
                        binding.listView.setAdapter(arrayAdapter);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> {
                        // User cancelled the dialog
                    });

            AlertDialog d = builder.create();
            d.setTitle(getString(R.string.deletenote));
            d.show();

        });

        binding.btnSave.setOnClickListener(v->{
            if(dbHelper.insertContact(binding.edtNotes.getText().toString())){
                Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                ArrayList<String> array_list1 = dbHelper.getAllData();
                arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list1);
                binding.listView.setAdapter(arrayAdapter);
                binding.edtNotes.setText("");
            } else{
                Toast.makeText(getApplicationContext(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });

        binding.imgBack.setOnClickListener(v-> finish());

    }
}