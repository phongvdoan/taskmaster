package com.phongvdoan.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListTasksQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import javax.annotation.Nonnull;

public class TaskDetail extends AppCompatActivity {

    private String TAG = "pvd.taskDetail";
    TaskDatabase taskDatabase;

    List<Task> taskList = new LinkedList<>();

    //declare awsclient
    private AWSAppSyncClient awsAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Log.i(TAG, "Created");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Started");
        //connect to local DB
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_database").allowMainThreadQueries().build();

        //connect to AWS
        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

//        getTasksFromDynamoDB();

////        String id = getIntent().getStringExtra("id");
//        String title = getIntent().getStringExtra("title");
//        String body = getIntent().getStringExtra("body");
//        String state = getIntent().getStringExtra("state");

        String id = getIntent().getStringExtra("id");
        Log.i(TAG, id + " was clicked");
        Task oneTask = taskDatabase.taskDao().getOne(id);
        TextView taskTextVeiw = findViewById(R.id.taskTitle);
        taskTextVeiw.setText(oneTask.title);
        TextView desciptTextVeiw = findViewById(R.id.taskBody);
        desciptTextVeiw.setText(oneTask.body);
        TextView statusTextVeiw = findViewById(R.id.taskStatus);
        statusTextVeiw.setText(oneTask.state);
        ImageView imageView = findViewById(R.id.taskImage);
        String url = "https://taskmasterb8b8d3a388424cb587c7f95d04e007f5185518-todo.s3-us-west-2.amazonaws.com/" + oneTask.uri;
//        imageView.setImageURI(Uri.parse(url));
        Picasso.get()
                .load(url)
                .resize(50, 50)
                .centerCrop()
                .into(imageView);


//        System.out.println(id);
//
//       for(Task task : taskList){
//           if(id == task.dynamoDBID){
//               System.out.println("task.dynamoDBID = " + task.dynamoDBID);
//               TextView taskTextVeiw = findViewById(R.id.taskTitle);
//                taskTextVeiw.setText(task.title);
//                TextView desciptTextVeiw = findViewById(R.id.taskBody);
//                desciptTextVeiw.setText(task.body);
//                TextView statusTextVeiw = findViewById(R.id.taskStatus);
//                statusTextVeiw.setText(task.state);
//           }
//       }
//
//        Task oneTask = taskDatabase.taskDao().getOne(id);
//        TextView taskTextVeiw = findViewById(R.id.taskTitle);
//        taskTextVeiw.setText(oneTask.title);
//        TextView desciptTextVeiw = findViewById(R.id.taskBody);
//        desciptTextVeiw.setText(oneTask.body);
//        TextView statusTextVeiw = findViewById(R.id.taskStatus);
//        statusTextVeiw.setText(oneTask.state);

    }

//    public void getTasksFromDynamoDB(){
//        awsAppSyncClient.query(ListTasksQuery.builder().build())
//                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
//                .enqueue(getTasksFromDynamoDBCallback);
//    }
//
//    private GraphQLCall.Callback<ListTasksQuery.Data> getTasksFromDynamoDBCallback = new GraphQLCall.Callback<ListTasksQuery.Data>() {
//        @Override
//        public void onResponse(@Nonnull Response<ListTasksQuery.Data> response) {
//            Log.i(TAG, response.data().listTasks().items().toString());
//
//            if(taskList.size() == 0 || response.data().listTasks().items().size() != taskList.size()) {
//                taskList.clear();
//                for (ListTasksQuery.Item item : response.data().listTasks().items()) {
//                    Task retrievedTask = new Task(item.title(), item.body(), item.state(), item.id());
//                    taskList.add(retrievedTask);
//                }
//
//            }
//        }
//
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e(TAG, e.toString());
//        }
//    };


}