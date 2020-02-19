package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.prefs.PreferenceChangeEvent;

public class TaskDetail extends AppCompatActivity {

    public TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

    }


    @Override
    protected void onResume() {
        super.onResume();

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_database").allowMainThreadQueries().build();

        Long id = getIntent().getLongExtra("id", 0);
        Task oneTask = taskDatabase.taskDao().getOne(id);
        TextView taskTextVeiw = findViewById(R.id.taskTitle);
        taskTextVeiw.setText(oneTask.title);
        TextView desciptTextVeiw = findViewById(R.id.taskBody);
        desciptTextVeiw.setText(oneTask.body);
        TextView statusTextVeiw = findViewById(R.id.taskStatus);
        statusTextVeiw.setText(oneTask.state);

    }
}
