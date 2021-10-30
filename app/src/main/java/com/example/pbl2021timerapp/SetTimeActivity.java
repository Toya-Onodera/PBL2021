package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.widget.TextView;

public class SetTimeActivity extends AppCompatActivity {
    private TextView setTimeTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        setTimeTextView = findViewById(R.id.setTimeView);
        setTimeTextView.setOnClickListener(view -> {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });
    }

    public void onReturnChooseTime(int hourOfDay, int minute) {
        setTimeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
    }
}