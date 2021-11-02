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

public class TimeDataManager {
    private static final int INSERT = 1;
    private static final int DELETE = 2;
    private static final int READ = 100;
    private static final int DELETE_ALL = 102;

    private TimeDao timeDao;
    private TimeRoomDatabase db;

    /**
     * 全ユーザーのデータを格納する List 型オブジェクト。
     */
    private List<Time> times;

    /**
     * 処理結果を通知する callback クラス。
     */
    private TimeDataManagerCallback callback;

    /**
     * 非同期処理を行う worker スレッド用のシングルスレッド。
     */

    /**
     * コンストラクタ
     *
     * @param context Application Context
     */
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * コンストラクタ
     *
     * @param context Application Context
     */
    public TimeDataManager(Context context) {
        db = TimeRoomDatabase.getDatabase(context);
        timeDao = db.TimeDao();
    }

    public void setCallback(TimeDataManagerCallback callback) {
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
     * read処理
     */
    public void read() {
        asyncRead();
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
                    case DELETE:
                    case DELETE_ALL:
                        callback.onDeleteUserCompleted();
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
     * 非同期でDB読み込みを行う
     *
     * @return 読み込み結果
     */
    @UiThread
    private void asyncRead() {
        //ワーカースレッドからDB読み込み結果を受け取る。
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.obj != null) {
                    times = (List<Time>) msg.obj;

                    // 処理が完了したら、callbackに処理を返す
                    callback.onReadUserCompleted(times);
                }
            }
        };

        BackgroundTaskRead backgroundTaskRead = new BackgroundTaskRead(handler, timeDao, times);

        // ワーカースレッドで実行する
        executorService.submit(backgroundTaskRead);
    }

    /**
     * insert, deleteの非同期処理を行うためのインナークラス
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
                case DELETE:
                    timeDao.delete(time);
                    handler.sendMessage(handler.obtainMessage(DELETE));
                    break;
                case DELETE_ALL:
                    timeDao.deleteAll();
                    handler.sendMessage(handler.obtainMessage(DELETE_ALL));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * readの非同期処理を行うためのインナークラス
     */
    private static class BackgroundTaskRead implements Runnable {
        private final Handler handler;
        private TimeDao timeDao;
        private List<Time> times;

        BackgroundTaskRead(Handler handler, TimeDao timeDao, List<Time> times) {
            this.handler = handler;
            this.timeDao = timeDao;
            this.times = times;
        }

        @WorkerThread
        @Override
        public void run() {
            // 非同期処理を開始する
            times = timeDao.loadTimes();
            handler.sendMessage(handler.obtainMessage(READ, times));
        }
    }

    /**
     * DBを閉じる
     */
    public void closeDatabase() {
        db.closeDatabase();
    }
}
