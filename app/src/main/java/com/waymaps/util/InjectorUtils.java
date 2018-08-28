package com.waymaps.util;


import android.content.Context;

import com.waymaps.data.AppRepository;
import com.waymaps.data.local.db.AppDatabase;
import com.waymaps.data.local.db.AppLocalDataSource;
import com.waymaps.data.remote.AppNetworkDataSource;

public class InjectorUtils {

    public static AppRepository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        AppNetworkDataSource networkDataSource = provideNetworkDataSource(context);
        AppLocalDataSource localDataSource = provideLocalDataSource(context);
        return AppRepository.getInstance(networkDataSource,localDataSource, executors);
    }

    public static AppNetworkDataSource provideNetworkDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        return AppNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static AppLocalDataSource provideLocalDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return AppLocalDataSource.getInstance(context.getApplicationContext(), executors,appDatabase);
    }

}
