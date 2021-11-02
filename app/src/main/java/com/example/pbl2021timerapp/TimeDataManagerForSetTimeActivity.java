package com.example.pbl2021timerapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeDataManagerForSetTimeActivity {
    private static final int INSERT = 1;

    private TimeDao timeDao;
    private TimeRoomDatabase db;

    /**
     * 処理結果を通知する callback クラス。
     */
    private TimeDataManagerCallbackForSetTimeActivity callback;

    /**
     * 非同期処理を行う worker スレッド用のシングルスレッド。
     */
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * コンストラクタ
     *
     * @param context Application Context
     */
    public TimeDataManagerForSetTimeActivity(Context context) {
        db = TimeRoomDatabase.getDatabase(context);
        timeDao = db.TimeDao();
    }

    public void setCallback(TimeDataManagerCallbackForSetTimeActivity callback) {
        this.callback = callback;
        callback.setTimeDataManager(this);
    }

    /**
     * insert処理
     *
     * @param time 追加する Time
     */
    public void insert(Time time) {
        asyncExecute(time, INSERT);
    }

    /**
     * background タスクを作り、非同期処理を行う
     *
     * @param time ToDebugItems のアイテム。
     */
    @UiThread
    private void asyncExecute(Time time, int type) {
        // ワーカースレッドから処理完了通知を受け取る
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case INSERT:
                        callback.onAddUserCompleted();
                        break;
                    default:
                        break;
                }
            }
        };

        BackgroundTask backgroundTask = new BackgroundTask(handler, timeDao, time, type);

        // ワーカースレッドで実行する
        executorService.submit(backgroundTask);
    }

    /**
     * insert の非同期処理を行うためのインナークラス
     */
    private static class BackgroundTask implements Runnable {
        private final Handler handler;
        private TimeDao timeDao;
        private Time time;
        private int type;

        BackgroundTask(Handler handler, TimeDao timeDao, Time time, int type) {
            this.handler = handler;
            this.timeDao = timeDao;
            this.time = time;
            this.type = type;
        }

        @WorkerThread
        @Override
        public void run() {
            // 非同期処理を開始する
            switch (type) {
                case INSERT:
                    timeDao.insert(time);
                    handler.sendMessage(handler.obtainMessage(INSERT));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * DBを閉じる
     */
    public void closeDatabase() {
        db.closeDatabase();
    }
}
