package com.phongvdoan.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 2, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
