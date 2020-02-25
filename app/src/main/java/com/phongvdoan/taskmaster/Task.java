package com.phongvdoan.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    long id;

    public String dynamoDBID;
    String title;
    String body;
    String state;

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public Task(String title, String body, String state, String id) {
        this.title = title;
        this.body = body;
        this.state = state;
        this.dynamoDBID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getDynamoDBID() {
        return dynamoDBID;
    }

    public void setDynamoDBID(String dynamoDBID) {
        this.dynamoDBID = dynamoDBID;
    }
}
