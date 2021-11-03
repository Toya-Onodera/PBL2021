package com.example.pbl2021timerapp.data_manager.set_time;

import android.content.Context;

import com.example.pbl2021timerapp.view.set_time.SetTimeActivity;

public class TimeDataManagerCallback {
    private SetTimeActivity activity;
    private TimeDataManager timeDataManager;

    /**
     * コンストラクタ
     *
     * @param context
     */
    public TimeDataManagerCallback(Context context) {
        activity = (SetTimeActivity) context;
    }

    public void setTimeDataManager(TimeDataManager timeDataManager) {
        this.timeDataManager = timeDataManager;
    }

    public void onAddUserCompleted() {
        activity.finish();
    }
}
