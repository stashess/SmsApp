package com.waymaps.data.local.pref;

import android.content.Context;

import com.waymaps.data.local.db.AppDatabase;
import com.waymaps.data.local.db.AppLocalDataSource;
import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalPreferenceDataSource {


    private static final String LOG_TAG = LocalPreferenceDataSource.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalPreferenceDataSource.class);

    private static final Object LOCK = new Object();
    private static LocalPreferenceDataSource sInstance;
    private final Context mContext;

    private static final String SERVICE_STATUS = "SERVICE_STATUS";

    public static LocalPreferenceDataSource getInstance(Context context) {
        LOGGER.debug(LOG_TAG, "Getting the local preference data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalPreferenceDataSource(context.getApplicationContext());
                LOGGER.debug(LOG_TAG, "Made new local preference data source");
            }
        }
        return sInstance;
    }

    private LocalPreferenceDataSource(Context mContext) {
        this.mContext = mContext;
    }

    public String getServiceStatus(){
        return LocalPreferenceManager.getStringValue(mContext, SERVICE_STATUS);
    }

    public void setServiceStatus(String s){
        LocalPreferenceManager.setStringValue(mContext, SERVICE_STATUS, s);
    }

    public void setDefaultServiceStatus(){
        String stringValue = LocalPreferenceManager.getStringValue(mContext, SERVICE_STATUS);
        if (LocalPreferenceManager.INVALID_STRING.equals(stringValue)){
            LocalPreferenceManager.setStringValue(mContext,SERVICE_STATUS,ServiceStatus.OFF);
        }
    }

}
