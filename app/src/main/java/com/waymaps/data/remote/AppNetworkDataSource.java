package com.waymaps.data.remote;

import android.content.Context;

import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppNetworkDataSource {

    // For Singleton instantiation

    private static final String LOG_TAG = AppNetworkDataSource.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppNetworkDataSource.class);

    private static final Object LOCK = new Object();
    private static AppNetworkDataSource sInstance;
    private final Context mContext;

    private final AppExecutors mExecutors;

    private AppNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
    }

    /**
     * Get the singleton for this class
     */
    public static AppNetworkDataSource getInstance(Context context, AppExecutors executors) {
        LOGGER.debug(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppNetworkDataSource(context.getApplicationContext(), executors);
                LOGGER.debug(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }
}
