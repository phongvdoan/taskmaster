package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.prefs.PreferenceChangeEvent;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView taskTextVeiw = findViewById(R.id.taskTitle);
        String newTask = getIntent().getStringExtra("task");
        taskTextVeiw.setText(newTask);
    }
}
