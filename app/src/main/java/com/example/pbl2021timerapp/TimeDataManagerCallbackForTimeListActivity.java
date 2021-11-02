package com.example.pbl2021timerapp;

import android.content.Context;

import java.util.List;

public class TimeDataManagerCallbackForTimeListActivity {
    private TimerListActivity activity;
    private TimeDataManagerForTimeListActivity timeDataManagerForTimeListActivity;

    /**
     * コンストラクタ
     *
     * @param context
     */
    public TimeDataManagerCallbackForTimeListActivity(Context context) {
        activity = (TimerListActivity) context;
    }

    public void setTimeDataManager(TimeDataManagerForTimeListActivity timeDataManagerForTimeListActivity) {
        this.timeDataManagerForTimeListActivity = timeDataManagerForTimeListActivity;
    }

    public void onReadUserCompleted(List<Time> times) {
        activity.updateRecyclerView(times);
    }

    public void onAddUserCompleted() {
        timeDataManagerForTimeListActivity.read();
    }

    public void onDeleteUserCompleted() {
        timeDataManagerForTimeListActivity.read();
    }
}
