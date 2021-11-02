package com.example.pbl2021timerapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Time {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id")
    private int id;

    @ColumnInfo(name = "time")
    private String timeStr;

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public int getId() {
        return id;
    }

    public String getTimeStr() {
        return timeStr;
    }
}
