package com.example.pbl2021timerapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SetTimeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "set_time_str")
    private String setTimeStr;

    public void setId(int id) {
        this.id = id;
    }

    public void setSetTimeStr(String setTimeStr) {
        this.setTimeStr = setTimeStr;
    }

    public int getId() {
        return id;
    }

    public String getSetTimeStr() {
        return setTimeStr;
    }
}
