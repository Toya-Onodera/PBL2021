package com.example.pbl2021timerapp.db.time;

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

    @ColumnInfo(name="is_set")
    private boolean isSet;

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public void setIsSet(boolean isSet ){ this.isSet = isSet; }

    public int getId() {
        return id;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public  boolean getIsSet(){return  isSet;}
}
