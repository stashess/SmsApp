package com.waymaps.util;


import android.content.Context;

import com.waymaps.data.AppRepository;
import com.waymaps.data.local.db.AppDatabase;
import com.waymaps.data.local.db.AppLocalDataSource;
import com.waymaps.data.local.pref.LocalPreferenceDataSource;
import com.waymaps.data.remote.AppNetworkDataSource;
import com.waymaps.data.remote.MailListener;
import com.waymaps.data.remote.MailChecker;
import com.waymaps.data.remote.MailSender;

public class InjectorUtils {

    public static AppRepository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        AppNetworkDataSource networkDataSource = provideNetworkDataSource(context);
        AppLocalDataSource localDataSource = provideLocalDataSource(context);
        LocalPreferenceDataSource localPreferenceDataSource = LocalPreferenceDataSource.getInstance(context);
        return AppRepository.getInstance(networkDataSource,localDataSource,localPreferenceDataSource, executors);
    }

    public static AppNetworkDataSource provideNetworkDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        Context applicationContext = context.getApplicationContext();
        MailChecker mailChecker = MailChecker.getInstance(context);
        MailSender mailSender = MailSender.getInstance(context);
        MailListener mailListener = MailListener.getInstance(context, mailChecker,mailSender);
        return AppNetworkDataSource.getInstance(applicationContext, executors, mailListener);
    }

    public static AppLocalDataSource provideLocalDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return AppLocalDataSource.getInstance(context.getApplicationContext(), executors,appDatabase);
    }

}
