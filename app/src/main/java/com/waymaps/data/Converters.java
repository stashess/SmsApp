package com.waymaps.data;

import android.arch.persistence.room.TypeConverter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Long toLong(Date date){
        if (date == null){
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.getTimeInMillis();
    }

    @TypeConverter
    public static Date toDate(Long l){
        if (l == null){
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(l);
        return instance.getTime();
    }
}
