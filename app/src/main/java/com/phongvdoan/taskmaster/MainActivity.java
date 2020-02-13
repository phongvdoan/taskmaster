package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView taskTextView = findViewById(R.id.userTask);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", "user");
        if(username == ""){
            username = "user";
        }
        taskTextView.setText(username + "'s tasks.");

        Button addTaskButton = findViewById(R.id.button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToAddTaskIntent = new Intent(MainActivity.this, AddTask.class);
                MainActivity.this.startActivity(goToAddTaskIntent);
            }
        });

        Button allTasksButton = findViewById(R.id.button2);
        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotToAllTasksIntent = new Intent(MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(gotToAllTasksIntent);
            }
        });

        Button doHomeworkButton = findViewById(R.id.doHomeworkButton);
        doHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotToTaskDetailIntent = new Intent(MainActivity.this, TaskDetail.class);
                TextView doHomework = findViewById(R.id.doHomeworkText);
                gotToTaskDetailIntent.putExtra("task", doHomework.getText().toString());
                MainActivity.this.startActivity(gotToTaskDetailIntent);
            }
        });

        Button doChoresButton = findViewById(R.id.doChoresButton);
        doChoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotToTaskDetailIntent = new Intent(MainActivity.this, TaskDetail.class);
                TextView doChores = findViewById(R.id.doChoresText);
                gotToTaskDetailIntent.putExtra("task", doChores.getText().toString());
                MainActivity.this.startActivity(gotToTaskDetailIntent);
            }
        });

        Button cookButton = findViewById(R.id.cookButton);
        cookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotToTaskDetailIntent = new Intent(MainActivity.this, TaskDetail.class);
                TextView cook = findViewById(R.id.cookText);
                gotToTaskDetailIntent.putExtra("task", cook.getText().toString());
                MainActivity.this.startActivity(gotToTaskDetailIntent);
            }
        });

        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotToSettingslIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(gotToSettingslIntent);
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        TextView taskTextView = findViewById(R.id.userTask);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", "user");
        taskTextView.setText(username + "'s tasks.");

    }
}
