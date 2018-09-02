package com.waymaps.data.local.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.waymaps.data.model.Mail;

import java.util.List;

@Dao
public interface MailDao {
    @Query("SELECT * FROM mail")
    List<Mail> getMails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Mail... mails);

    @Query("DELETE FROM mail WHERE mail like :mail")
    void deleteByMail(String mail);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void editMail(Mail mail);
}
