package com.waymaps.jobscheduler.util;

import android.os.Handler;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by StanislavCheslavskyi on 21.09.2018.
 */

public class JobHandler {

    private static final String LOG_TAG = JobHandler.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(JobHandler.class);
    private static final Object LOCK = new Object();

    private Map<String,Handler> stringHandlerMap;

    private static JobHandler sInstance;

    public static JobHandler getInstance() {
        LOGGER.debug(LOG_TAG, "Getting the jobHandler");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new JobHandler();
                LOGGER.debug(LOG_TAG, "Made new jobHandler");
            }
        }
        return sInstance;
    }

    private JobHandler() {
        stringHandlerMap = new HashMap<>();
    }

    public void deleteTaskByTag(String tag){
        stringHandlerMap.get(tag).removeCallbacksAndMessages(null);
    }

    public void addTaskWithTag(String tag,Handler handler){
        stringHandlerMap.put(tag,handler);
    }

    public void deleteAllTask(){
        for (Handler handler : stringHandlerMap.values()){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
