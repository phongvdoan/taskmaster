package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class AllTasks extends AppCompatActivity implements MyTaskRecyclerViewAdapter.TaskListener {

    private List<Task> taskList = new LinkedList<>();
    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_database").allowMainThreadQueries().build();


    }

    @Override
    protected void onResume() {
        super.onResume();

        this.taskList = taskDatabase.taskDao().getAll();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(this.taskList,null));


    }

    @Override
    public void onClickOnTaskCallback(Task task) {
        String stringForToast = String.format("%s %s %s", task.title, task.body, task.state);
        Toast saveToast = Toast.makeText(getApplicationContext(), stringForToast, Toast.LENGTH_SHORT);
        saveToast.show();
    }
}
