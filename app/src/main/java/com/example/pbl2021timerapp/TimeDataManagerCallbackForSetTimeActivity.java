package com.example.pbl2021timerapp;

import android.content.Context;

public class TimeDataManagerCallbackForSetTimeActivity {
    private SetTimeActivity activity;
    private TimeDataManagerForSetTimeActivity timeDataManager;

    /**
     * コンストラクタ
     *
     * @param context
     */
    public TimeDataManagerCallbackForSetTimeActivity(Context context) {
        activity = (SetTimeActivity) context;
    }

    public void setTimeDataManager(TimeDataManagerForSetTimeActivity timeDataManager) {
        this.timeDataManager = timeDataManager;
    }

    public void onAddUserCompleted() {
        activity.finish();
    }
}
