package com.waymaps.data.local.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.waymaps.data.model.PhoneNumber;

import java.util.List;

@Dao
public interface PhoneDao {

    @Query("SELECT * FROM phone")
    List<PhoneNumber> getPhones();

    @Insert
    void bulkInsert(PhoneNumber... phoneNumbers);

    @Query("DELETE FROM phone WHERE phoneNo like :phoneNo")
    void deleteByPhoneNo(String phoneNo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void editPhone(PhoneNumber phoneNumber);

}

