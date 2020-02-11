package com.phongvdoan.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button submitButtom = findViewById(R.id.button);
        submitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView submitText = findViewById(R.id.textView4);
                submitText.setVisibility(View.VISIBLE);

            }
        });
    }
}
