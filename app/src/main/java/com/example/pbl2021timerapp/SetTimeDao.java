package com.example.pbl2021timerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SetTimeDao {
    @Query("select * from settime")
    List<SetTime> getAll();

    @Query("select * from settime where id in (:ids)")
    List<SetTime> loadAllIds(int[] ids);

    @Insert
    void insertAll(SetTime... setTimes);

    @Insert
    void insert(SetTime setTime);

    @Delete
    void delete(SetTime setTime);
}
