package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateTaskMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreateTaskInput;

public class AddTask extends AppCompatActivity {

    private String TAG= "pvd.addTask";

    TaskDatabase taskDatabase;
    private AWSAppSyncClient awsAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        Button submitButtom = findViewById(R.id.button);
        submitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText titleEditText = findViewById(R.id.titleEditText);
                String newTitle = titleEditText.getText().toString();
                EditText descriptEditText = findViewById(R.id.desciptEditText);
                String newDescription = descriptEditText.getText().toString();


                addOneTaskToDynamoDB(newTitle, newDescription);

                //Toasts
                Toast submitToast = Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_SHORT);
                submitToast.show();
//                Intent gotToMainActivityIntent = new Intent(AddTask.this, MainActivity.class);
//                AddTask.this.startActivity(gotToMainActivityIntent);

            }
        });
    }

    public void addOneTaskToDynamoDB(String title, String body){
        CreateTaskInput createTaskInput = CreateTaskInput.builder().
                title(title).
                body(body).
                state("New").
                build();

        awsAppSyncClient.mutate(CreateTaskMutation.builder().input(createTaskInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateTaskMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateTaskMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTaskMutation.Data> response) {
            Log.i(TAG, "Added Task");
            String dynamoDBID = response.data().createTask().id();
            String title = response.data().createTask().title();
            String body = response.data().createTask().body();
            Task newTask = new Task(title,body, "New", dynamoDBID);
            taskDatabase.taskDao().save(newTask);
            Intent gotToMainActivityIntent = new Intent(AddTask.this, MainActivity.class);
            AddTask.this.startActivity(gotToMainActivityIntent);
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(TAG, e.toString());
        }
    };
}
