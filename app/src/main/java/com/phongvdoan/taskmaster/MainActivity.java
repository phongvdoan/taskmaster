package com.phongvdoan.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListTasksQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    private String TAG = "pvd.main";

    private List<Task> taskList = new LinkedList<>();

    //declare local database
    private TaskDatabase taskDatabase;

    //declare awsclient
    private AWSAppSyncClient awsAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect to local database
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_database").allowMainThreadQueries().build();

        //connect to AWS
        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

//        this.taskList = taskDatabase.taskDao().getAll();

        //call method to retrieve tasks from AWS
        taskList = new LinkedList<>();
        getTasksFromDynamoDB();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(this.taskList, null, this));

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

                Intent gotToSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(gotToSettingsIntent);
            }
        });




    }

    @SuppressLint("SetTextI18n")
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

    public void getTasksFromDynamoDB(){
        awsAppSyncClient.query(ListTasksQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(getTasksFromDynamoDBCallback);
    }

    private GraphQLCall.Callback<ListTasksQuery.Data> getTasksFromDynamoDBCallback = new GraphQLCall.Callback<ListTasksQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListTasksQuery.Data> response) {
            Log.i(TAG, response.data().listTasks().items().toString());

            if(taskList.size() == 0 || response.data().listTasks().items().size() != taskList.size()) {
               taskList.clear();
                for (ListTasksQuery.Item item : response.data().listTasks().items()) {
                    Task retrievedTask = new Task(item.title(), item.body(), item.state(), item.id());
                    taskList.add(retrievedTask);
                }

            }
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(TAG, e.toString());
        }
    };

}
