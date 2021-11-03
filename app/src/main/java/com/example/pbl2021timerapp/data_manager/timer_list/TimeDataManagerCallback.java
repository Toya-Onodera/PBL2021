package com.example.pbl2021timerapp.data_manager.timer_list;

import android.content.Context;

import com.example.pbl2021timerapp.db.time.Time;
import com.example.pbl2021timerapp.view.timer_tist.TimerListActivity;

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
