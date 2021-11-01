package com.example.pbl2021timerapp;

import android.content.Context;

import androidx.room.Room;

public class SetTimeDatabaseSingleton {
    private static SetTimeDatabase instance = null;

    public static SetTimeDatabase getInstance(Context context) {
        if (instance != null) { return instance; }
        instance = Room.databaseBuilder(context, SetTimeDatabase.class, "set-time").build();
        return instance;
    }
}
