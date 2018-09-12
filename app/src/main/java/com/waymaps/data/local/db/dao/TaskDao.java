package com.waymaps.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.waymaps.data.model.PhoneNumber;
import com.waymaps.data.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getTaskHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Task... taskHistories);

    @Query("DELETE FROM tasks WHERE status = 1")
    void deleteAllTaskWithStatusDone();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void ediTask(Task task);

}
