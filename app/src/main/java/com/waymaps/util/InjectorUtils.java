package com.waymaps.util;


import android.content.Context;

import com.waymaps.data.AppRepository;
import com.waymaps.data.local.db.AppDatabase;
import com.waymaps.data.local.db.AppLocalDataSource;
import com.waymaps.data.remote.AppNetworkDataSource;
import com.waymaps.data.remote.MailListener;
import com.waymaps.data.remote.MailChecker;

public class InjectorUtils {

    public static AppRepository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        AppNetworkDataSource networkDataSource = provideNetworkDataSource(context);
        AppLocalDataSource localDataSource = provideLocalDataSource(context);
        return AppRepository.getInstance(networkDataSource,localDataSource, executors);
    }

    public static AppNetworkDataSource provideNetworkDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        Context applicationContext = context.getApplicationContext();
        MailChecker mailChecker = MailChecker.getInstance(context);
        MailListener mailListener = MailListener.getInstance(context, mailChecker);
        return AppNetworkDataSource.getInstance(applicationContext, executors, mailListener);
    }

    public static AppLocalDataSource provideLocalDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return AppLocalDataSource.getInstance(context.getApplicationContext(), executors,appDatabase);
    }

}
