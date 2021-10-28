package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.openSetTimeActivityButton).setOnClickListener(view -> {
            // クリック時の処理
            onButtonClick(view);
        });
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(this, SetTimeActivity.class);
        startActivity(intent);
    }
}