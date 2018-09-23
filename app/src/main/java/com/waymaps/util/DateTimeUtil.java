package com.waymaps.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String toFormat(Date date, SimpleDateFormat dateFormat){
        return dateFormat.format(date);
    }

}
