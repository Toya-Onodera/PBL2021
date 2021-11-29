package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class GoTimerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button timerStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_timer);

        timerStopButton = findViewById(R.id.timetStopTextButton);

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
            mediaPlayer.start();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        timerStopButton.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}

