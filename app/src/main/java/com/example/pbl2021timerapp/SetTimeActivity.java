package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SetTimeActivity extends AppCompatActivity {
    // DB 関連の動作を包括するクラス
    private TimeDataManagerForSetTimeActivity timeDataManager;

    // DB 関連の動作で使用するコールバック
    private TimeDataManagerCallbackForSetTimeActivity callback;

    // UI に使用するプロパティ
    private TextView setTimeTextView;
    private Button setTimerButton;

    // 時間を文字列で保持するプロパティ
    private String timeStr = "00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        // Room を使用し、データを挿入するための準備を行う
        timeDataManager = new TimeDataManagerForSetTimeActivity(this);
        callback = new TimeDataManagerCallbackForSetTimeActivity(this);
        timeDataManager.setCallback(callback);

        // 時間指定を行う際の処理を以下に実装する
        setTimeTextView = findViewById(R.id.setTimeView);
        setTimeTextView.setOnClickListener(view -> {
            // Android の DatePickerFragment を表示する
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });

        // タイマーセットの処理を以下に実装する
        setTimerButton = findViewById(R.id.setTimerButton);
        setTimerButton.setOnClickListener(view -> {
            // TODO: DB に保存する処理を書く
            Time saveTime = new Time();
            saveTime.setTimeStr(timeStr);
            timeDataManager.insert(saveTime);
        });
    }

    public void onReturnChooseTime(int hourOfDay, int minute) {
        timeStr = String.format("%02d:%02d", hourOfDay, minute);
        setTimeTextView.setText(timeStr);
    }

    @Override
    protected void onDestroy() {
        // Activity 破棄のタイミングで DB を閉じる
        timeDataManager.closeDatabase();
        super.onDestroy();
    }
}