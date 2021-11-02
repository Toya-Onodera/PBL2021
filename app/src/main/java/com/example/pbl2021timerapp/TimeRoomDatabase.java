package com.example.pbl2021timerapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Time.class}, version = 1, exportSchema = false)
public abstract class TimeRoomDatabase extends RoomDatabase {
    /**
     * DB 操作に使用する DAO の抽象メソッド
     *
     * @return TimeDao のオブジェクト
     */
    public abstract TimeDao TimeDao();

    private static TimeRoomDatabase timeRoomDatabase;

    /**
     * Room データベースを返す
     * 存在しなければ、作成する
     *
     * @param context
     * @return TimeRoomDatabase
     */
    public static TimeRoomDatabase getDatabase(final Context context) {
        if (timeRoomDatabase == null) {
            synchronized (TimeRoomDatabase.class) {
                if (timeRoomDatabase == null) {
                    // DBを作成する
                    timeRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            TimeRoomDatabase.class, "time_database")
                            .build();
                }
            }
        }

        return timeRoomDatabase;
    }

    /**
     * DBを閉じる
     */
    public void closeDatabase() {
        if (timeRoomDatabase.isOpen()) {
            timeRoomDatabase.close();
        }

        timeRoomDatabase = null;
    }
}
