package com.example.pbl2021timerapp.view.timer_tist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.data_manager.timer_list.TimeDataManagerCallback;
import com.example.pbl2021timerapp.data_manager.timer_list.TimeDataManager;
import com.example.pbl2021timerapp.db.time.Time;
import com.example.pbl2021timerapp.view.set_time.SetTimeActivity;
import com.example.pbl2021timerapp.view.set_time.TimeRecyclerViewAdapter;

import java.util.List;

public class TimerListActivity extends AppCompatActivity {
    // DB 関連の動作を包括するクラス
    private TimeDataManager timeDataManager;

    // DB 関連の動作で使用するコールバック
    private TimeDataManagerCallback callback;

    // RecyclerView
    private RecyclerView _rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB をクリックしたときの処理を以下のコールバックで受け取る
        findViewById(R.id.openSetTimeActivityButton).setOnClickListener(view -> {
            // クリック時の処理
            this.onButtonClick();
        });

        // RecyclerView を取得する
        _rv = (RecyclerView) findViewById(R.id.timerListRecyclerView);

        // Room を使用し、データを取得する
        // 取得後は RecyclerView を更新するコールバックが実行される
        timeDataManager = new TimeDataManager(this);
        callback = new TimeDataManagerCallback(this);
        timeDataManager.setCallback(callback);
        timeDataManager.read();
    }

    private void onButtonClick() {
        Intent intent = new Intent(this, SetTimeActivity.class);
        startActivity(intent);
    }

    /**
     * DB 読み込み完了後、RecyclerView を更新する
     *
     * @param times DB から取得した SetTime
     */
    public void updateRecyclerView(List<Time> times) {
        TimeRecyclerViewAdapter adapter = new TimeRecyclerViewAdapter(times);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        _rv.setLayoutManager(layout);
        _rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeDataManager.read();
    }

    @Override
    protected void onDestroy() {
        // Activity 破棄のタイミングで DB を閉じる
        timeDataManager.closeDatabase();
        super.onDestroy();
    }
}