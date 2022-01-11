package com.example.pbl2021timerapp.media_manager;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class MediaManager {
    private Context _context;
    private MediaPlayer _mediaPlayer;

    public MediaManager(Context context, String filePath) {
        _context = context;
        mediaPlayerInit(filePath);
    }

    private void mediaPlayerInit(String filePath) {
        _mediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor audioFileDescriptor = _context.getAssets().openFd(filePath);

            // MediaPlayerに読み込んだ音楽ファイルを指定
            _mediaPlayer.setDataSource(audioFileDescriptor.getFileDescriptor(),
                    audioFileDescriptor.getStartOffset(),
                    audioFileDescriptor.getLength());

            // 音量調整を端末のボタンに任せる
            ((Activity) _context).setVolumeControlStream(AudioManager.STREAM_MUSIC);

            // 音声再生準備と再生
            _mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        _mediaPlayer.start();
    }

    public void stop() {
        _mediaPlayer.stop();
    }
}
