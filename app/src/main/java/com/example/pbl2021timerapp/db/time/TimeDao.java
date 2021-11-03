package com.example.pbl2021timerapp.db.time;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeDao {
    @Query("SELECT * FROM time")
    public List<Time> loadTimes();

    @Insert
    public void insert(Time time);

    @Update
    public void update(Time time);

    @Delete
    public void delete(Time time);

    @Query("DELETE FROM time")
    public void deleteAll();
}
