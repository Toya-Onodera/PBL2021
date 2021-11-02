package com.example.pbl2021timerapp;

import android.content.Context;

import java.util.List;

public class TimeDataManagerCallback {
    private TimerListActivity activity;
    private TimeDataManager timeDataManager;

    /**
     * コンストラクタ
     *
     * @param context
     */
    public TimeDataManagerCallback(Context context) {
        activity = (TimerListActivity) context;
    }

    public void setTimeDataManager(TimeDataManager timeDataManager) {
        this.timeDataManager = timeDataManager;
    }

    public void onReadUserCompleted(List<Time> times) {
        activity.updateRecyclerView(times);
    }

    public void onAddUserCompleted() {
        timeDataManager.read();
    }

    public void onDeleteUserCompleted() {
        timeDataManager.read();
    }
}
