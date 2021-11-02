package com.example.pbl2021timerapp;

import android.content.Context;

import androidx.room.Room;

public class SetTimeDatabaseSingleton {
    private static TimeRoomDatabase instance = null;

    public static TimeRoomDatabase getInstance(Context context) {
        if (instance != null) { return instance; }
        instance = Room.databaseBuilder(context, TimeRoomDatabase.class, "set-time").build();
        return instance;
    }
}
