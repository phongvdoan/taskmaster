package com.phongvdoan.taskmaster;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    long id;


    String dynamoDBId;
    String title;
    String body;
    String state;
    String uri;
    String cityName;
    String stateName;



    @Ignore
    public Task(String title, String body, String state, String uri, String cityName, String stateName) {
        this.title = title;
        this.body = body;
        this.state = state;
        this.uri = uri;
        this.cityName = cityName;
        this.stateName = stateName;

    }

    public Task(String title, String body, String state, String dynamoDBId, String uri, String cityName, String stateName) {
        this.title = title;
        this.body = body;
        this.state = state;
        this.dynamoDBId = dynamoDBId;
        this.uri = uri;
        this.cityName = cityName;
        this.stateName = stateName;
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

    public String getDynamoDBId() {
        return dynamoDBId;
    }

    public void setDynamoDBId(String dynamoDBId) {
        this.dynamoDBId = dynamoDBId;
    }
}