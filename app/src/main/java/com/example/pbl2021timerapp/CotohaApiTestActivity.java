package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.pbl2021timerapp.cotoha.CotohaApiManagerCallbacks;
import com.example.pbl2021timerapp.cotoha.CotohaApiManager;

public class CotohaApiTestActivity extends AppCompatActivity implements CotohaApiManagerCallbacks {
    private CotohaApiManager cotohaApiManager;
    private Button cotohaSendButton;
    private TextView answerCotohaText;
    private TextView inputCotohaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotoha_api_test);

        cotohaApiManager = new CotohaApiManager(this);

        answerCotohaText = findViewById(R.id.answerCotohaText);
        inputCotohaText = findViewById(R.id.inputCotohaText);
        cotohaSendButton = findViewById(R.id.cotohaSendButton);

        cotohaSendButton.setOnClickListener(view -> {
            cotohaApiManager.getSimilarity(
                    answerCotohaText.getText().toString(),
                    inputCotohaText.getText().toString()
            );
        });
    }

    @Override
    public void onTaskFinished(float score) {
        // TODO: score を使用してタイマーを止める動作を追加できそう
        Log.d("score(callback)", Float.toString(score));
    }
}