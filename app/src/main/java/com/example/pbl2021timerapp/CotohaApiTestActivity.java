package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pbl2021timerapp.cotoha.CotohaApiManeger;

public class CotohaApiTestActivity extends AppCompatActivity {
    private CotohaApiManeger cotohaApiManeger;
    private Button cotohaSendButton;
    private TextView answerCotohaText;
    private TextView inputCotohaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotoha_api_test);

        cotohaApiManeger = new CotohaApiManeger();

        answerCotohaText = findViewById(R.id.answerCotohaText);
        inputCotohaText = findViewById(R.id.inputCotohaText);
        cotohaSendButton = findViewById(R.id.cotohaSendButton);

        cotohaSendButton.setOnClickListener(view -> {
            cotohaApiManeger.getSimilarity(
                    answerCotohaText.getText().toString(),
                    inputCotohaText.getText().toString(),
                    getApplicationContext()
            );
        });
    }
}