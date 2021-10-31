package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SetTimeActivity extends AppCompatActivity {
    private TextView setTimeTextView;
    private String timeStr = "00:00";
    private Button setTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

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
        });
    }

    public void onReturnChooseTime(int hourOfDay, int minute) {
        timeStr = String.format("%02d:%02d", hourOfDay, minute);
        setTimeTextView.setText(timeStr);
    }
}