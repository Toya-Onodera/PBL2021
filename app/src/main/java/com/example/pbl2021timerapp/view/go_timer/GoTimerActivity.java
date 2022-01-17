package com.example.pbl2021timerapp.view.go_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.cotoha_manager.CotohaApiManager;
import com.example.pbl2021timerapp.cotoha_manager.CotohaApiManagerCallbacks;
import com.example.pbl2021timerapp.japan_news_manager.JapanNewsManager;
import com.example.pbl2021timerapp.japan_news_manager.JapanNewsManagerCallbacks;
import com.example.pbl2021timerapp.media_manager.MediaManager;
import com.example.pbl2021timerapp.recognizer_manager.SpeechRecognizerManager;
import com.example.pbl2021timerapp.recognizer_manager.SpeechRecognizerManagerCallbacks;

public class GoTimerActivity extends AppCompatActivity implements JapanNewsManagerCallbacks, SpeechRecognizerManagerCallbacks, CotohaApiManagerCallbacks {
    private Button timerStopButton;
    private TextView answerTextView;
    private TextView speechPreviewTextView;

    private MediaManager mediaManager = null;
    private SpeechRecognizerManager speechRecognizerManager = null;
    private CotohaApiManager cotohaApiManager = null;
    private JapanNewsManager japanNewsManager = null;

    private String answerText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_timer);

        timerStopButton = findViewById(R.id.timetStopTextButton);
        answerTextView = findViewById(R.id.answerText);
        speechPreviewTextView = findViewById(R.id.speechPreviewText);

        timerStopButton.setOnClickListener(v -> {
            finish();
        });

        japanNewsManager = new JapanNewsManager(this);
        mediaManager = new MediaManager(this, "01.mp3");
        speechRecognizerManager = new SpeechRecognizerManager(this, this, speechPreviewTextView);
        cotohaApiManager = new CotohaApiManager(this);

        japanNewsManager.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaManager.stop();
        speechRecognizerManager.destory();
    }

    @Override
    public void onReceivedNewsData(String speechText) {
        mediaManager.start();
        speechRecognizerManager.start();

        // FIXME: 習得したニュースタイトルの受け渡しができていない
        answerText = speechText;
        answerTextView.setText(speechText);
    }

    @Override
    public void onSpeechRecognizerFinished(String resultStr) {
        if (resultStr != "") {
            cotohaApiManager.getSimilarity(
                    answerText,
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

