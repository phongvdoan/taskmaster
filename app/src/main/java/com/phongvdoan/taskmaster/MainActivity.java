package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyTaskRecyclerViewAdapter.TaskListener {

    private List<Task> mValues = new LinkedList<>();
    private MyTaskRecyclerViewAdapter myTaskRecyclerViewAdapter;

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

//        Button doHomeworkButton = findViewById(R.id.taskbutton);
//        doHomeworkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent gotToTaskDetailIntent = new Intent(MainActivity.this, TaskDetail.class);
//                TextView doHomework = findViewById(R.id.doHomeworkText);
//                gotToTaskDetailIntent.putExtra("task", doHomework.getText().toString());
//                MainActivity.this.startActivity(gotToTaskDetailIntent);
//            }
//        });
//
//        Button doChoresButton = findViewById(R.id.doChoresButton);
//        doChoresButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent gotToTaskDetailIntent = new Intent(MainActivity.this, TaskDetail.class);
//                TextView doChores = findViewById(R.id.doChoresText);
//                gotToTaskDetailIntent.putExtra("task", doChores.getText().toString());
//                MainActivity.this.startActivity(gotToTaskDetailIntent);
//            }
//        });
//
//        Button cookButton = findViewById(R.id.cookButton);
//        cookButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent gotToTaskDetailIntent = new Intent(MainActivity.this, TaskDetail.class);
//                TextView cook = findViewById(R.id.cookText);
//                gotToTaskDetailIntent.putExtra("task", cook.getText().toString());
//                MainActivity.this.startActivity(gotToTaskDetailIntent);
//            }
//        });

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


    public void sendMessage(View view) {
        Intent intent = new Intent(this, TaskDetail.class);
        TextView title = findViewById(R.id.title);
        String titleString = title.getText().toString();
        intent.putExtra("task", titleString);
        startActivity(intent);

    }

    @Override
    public void taskClick(int position) {
        mValues.get(position);
        Intent intent = new Intent(this, TaskDetail.class);
        startActivity(intent);

    }
}
