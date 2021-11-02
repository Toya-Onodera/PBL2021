package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class TimerListActivity extends AppCompatActivity {
    private TimeDataManager timeDataManager;
    private TimeDataManagerCallback callback;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB をクリックしたときの処理を以下のコールバックで受け取る
        findViewById(R.id.openSetTimeActivityButton).setOnClickListener(view -> {
            // クリック時の処理
            this.onButtonClick(view);
        });

        // Room を使用し、データを取得する
        // 取得後は RecyclerView を更新するコールバックが実行される
        timeDataManager = new TimeDataManager(this);
        callback = new TimeDataManagerCallback(this);
        timeDataManager.setCallback(callback);
        timeDataManager.read();
    }

    private void onButtonClick(View view) {
        Intent intent = new Intent(this, SetTimeActivity.class);
        startActivity(intent);
    }

    /**
     * DB 読み込み完了後、RecyclerView を更新する
     *
     * @param times DB から取得した SetTime
     */
    public void updateRecyclerView(List<Time> times) {
        if (adapter instanceof TimeRecyclerViewAdapter) {
            ((TimeRecyclerViewAdapter) adapter).setTimes(times);
        }
    }

    @Override
    protected void onDestroy() {
        // Activity 破棄のタイミングで DB を閉じる
        timeDataManager.closeDatabase();
        super.onDestroy();
    }
}