package com.waymaps.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.waymaps.data.local.db.dao.PhoneDao;
import com.waymaps.data.model.PhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Database(entities = {PhoneNumber.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDatabase.class.getSimpleName());

    private static final String DATABASE_NAME = "smsDB";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        LOGGER.debug(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).build();
                LOGGER.debug(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    public abstract PhoneDao phoneDao();

}