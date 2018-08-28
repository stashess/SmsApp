package com.waymaps.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "phone",indices = @Index(value = "phoneNo",unique = true))
public class PhoneNumber {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String phoneNo;

    @Ignore
    public PhoneNumber(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public PhoneNumber(int id, String phoneNo) {
        this.id = id;
        this.phoneNo = phoneNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}