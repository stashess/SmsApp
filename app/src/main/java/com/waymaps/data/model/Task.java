package com.waymaps.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int status;

    private int type;

    private String text;

    private String receiver;

    private Date receivedDate;

    private Date lastTryDate;

    @Ignore
    public Task() {
    }

    public Task(int id, int status, int type, String text, String receiver, Date receivedDate, Date lastTryDate) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.text = text;
        this.receiver = receiver;
        this.receivedDate = receivedDate;
        this.lastTryDate = lastTryDate;
    }

    public interface Statuses{
        int TO_PROCESS = 0;
        int SUCCESS = 1;
        int FAILED = 2;
    }

    private interface Types{
        int PHONE_TO_MAIL = 0;
        int MAIL_TO_PHONE = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getLastTryDate() {
        return lastTryDate;
    }

    public void setLastTryDate(Date lastTryDate) {
        this.lastTryDate = lastTryDate;
    }
}
