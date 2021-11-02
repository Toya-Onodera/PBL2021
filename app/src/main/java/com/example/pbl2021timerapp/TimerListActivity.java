package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
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

        // データのあれこれの処理を以下に記載する
        // db = SetTimeDatabaseSingleton.getInstance(getApplicationContext());

        // RecyclerView の処理を以下に記載する
//        RecyclerView rv = (RecyclerView) findViewById(R.id.timerListRecyclerView);
//        TimerListRecyclerViewAdapter adapter = new TimerListRecyclerViewAdapter(this.createDateset());
//        LinearLayoutManager layout = new LinearLayoutManager(this);
//        rv.setLayoutManager(layout);
//        rv.setAdapter(adapter);

        timeDataManager = new TimeDataManager(this);
        callback = new TimeDataManagerCallback(this);
        timeDataManager.setCallback(callback);
        timeDataManager.read();
    }

    private void onButtonClick(View view) {
        Intent intent = new Intent(this, SetTimeActivity.class);
        startActivity(intent);
    }

    // FIXME: ダミーデータを生成する動作になっているので実データに書き換える
    private List<TimerListRowData> createDateset() {
        List<TimerListRowData> dataset = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            TimerListRowData data = new TimerListRowData();
            data.setTime("00:00");
            dataset.add(data);
        }

        return dataset;
    }

    /**
     * DB読み込み完了後、RecyclerView を更新する
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