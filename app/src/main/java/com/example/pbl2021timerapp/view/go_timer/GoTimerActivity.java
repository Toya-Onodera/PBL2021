package com.example.pbl2021timerapp.view.go_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.media_manager.MediaManager;
import com.example.pbl2021timerapp.recognizer.SpeechRecognizerManager;

public class GoTimerActivity extends AppCompatActivity {
    private Button timerStopButton;
    private TextView speechPreviewText;

    private MediaManager mediaManager = null;
    private SpeechRecognizerManager speechRecognizerManager = null;

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

        speechRecognizerManager = new SpeechRecognizerManager(this, speechPreviewText);
        speechRecognizerManager.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaManager.stop();
        speechRecognizerManager.destory();
    }
}

