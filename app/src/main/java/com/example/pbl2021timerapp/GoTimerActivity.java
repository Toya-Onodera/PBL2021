package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pbl2021timerapp.recognizer.SpeechRecognizerManager;

import java.io.IOException;


public class GoTimerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button timerStopButton;
    private TextView speechPreviewText;

    private SpeechRecognizerManager speechRecognizerManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_timer);

        timerStopButton = findViewById(R.id.timetStopTextButton);
        speechPreviewText = findViewById(R.id.speechPreviewText);

        // MediaPlayer のインスタンス生成
        mediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor afdescripter = getAssets().openFd("01.mp3");

            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());

            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);

            // 音声再生準備と再生
            mediaPlayer.prepare();


        } catch (IOException e1) {
            e1.printStackTrace();
        }

        timerStopButton.setOnClickListener(v -> {
            finish();
        });

        speechRecognizerManager = new SpeechRecognizerManager(this, speechPreviewText);
        speechRecognizerManager.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        speechRecognizerManager.destory();
    }
}

