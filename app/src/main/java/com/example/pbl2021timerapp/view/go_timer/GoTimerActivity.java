package com.example.pbl2021timerapp.view.go_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.cotoha_manager.CotohaApiManager;
import com.example.pbl2021timerapp.cotoha_manager.CotohaApiManagerCallbacks;
import com.example.pbl2021timerapp.media_manager.MediaManager;
import com.example.pbl2021timerapp.recognizer_manager.SpeechRecognizerManager;
import com.example.pbl2021timerapp.recognizer_manager.SpeechRecognizerManagerCallbacks;

public class GoTimerActivity extends AppCompatActivity implements SpeechRecognizerManagerCallbacks, CotohaApiManagerCallbacks {
    private Button timerStopButton;
    private TextView speechPreviewText;

    private MediaManager mediaManager = null;
    private SpeechRecognizerManager speechRecognizerManager = null;
    private CotohaApiManager cotohaApiManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_timer);

        timerStopButton = findViewById(R.id.timetStopTextButton);
        speechPreviewText = findViewById(R.id.speechPreviewText);

        timerStopButton.setOnClickListener(v -> {
            finish();
        });

        mediaManager = new MediaManager(this, "01.mp3");
        mediaManager.start();

        speechRecognizerManager = new SpeechRecognizerManager(this, this, speechPreviewText);
        speechRecognizerManager.start();

        cotohaApiManager = new CotohaApiManager(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaManager.stop();
        speechRecognizerManager.destory();
    }

    @Override
    public void onSpeechRecognizerFinished(String resultStr) {
        if (resultStr != "") {
            cotohaApiManager.getSimilarity(
                    "こんにちは",
                    resultStr
            );
        }
    }

    @Override
    public void onSimilarityTaskFinished(float score) {
        if (score >= 0.7) {
            finish();
        }
    }
}

