package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class AllTasks extends AppCompatActivity {


    private List<Task> taskList = new LinkedList<>();
    public TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_database").allowMainThreadQueries().build();
        this.taskList = taskDatabase.taskDao().getAll();


        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(this.taskList, null, this));

    }
}
