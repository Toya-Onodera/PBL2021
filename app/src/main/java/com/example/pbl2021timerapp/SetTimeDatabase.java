package com.example.pbl2021timerapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SetTime.class}, version = 1, exportSchema = false)
public abstract class SetTimeDatabase extends RoomDatabase {
    public abstract SetTimeDao setTimeDao();
}
