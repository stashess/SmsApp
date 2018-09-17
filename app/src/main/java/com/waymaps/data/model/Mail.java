package com.waymaps.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "mail",indices = @Index(value = "mail",unique = true))
public class Mail {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mail;

    private String password;

    private Date lastCheckDate;

    private int mainEmail;

    private int status;

    public Mail(int id, String mail, String password, Date lastCheckDate, int mainEmail, int status) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.lastCheckDate = lastCheckDate;
        this.mainEmail = mainEmail;
        this.status = status;
    }

    @Ignore
    public Mail() {
    }

    @Ignore
    public Mail(String mail, String password, Date lastCheckDate, int mainEmail, int status) {
        this.mail = mail;
        this.password = password;
        this.lastCheckDate = lastCheckDate;
        this.mainEmail = mainEmail;
        this.status = status;
    }

    public int getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(int mainEmail) {
        this.mainEmail = mainEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public interface Statuses{
        int NOT_CHECKED = 0;
        int VERIFIED = 1;
        int FAILED = 2;
    }
}
