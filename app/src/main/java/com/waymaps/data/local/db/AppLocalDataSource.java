package com.waymaps.data.local.db;

import android.content.Context;

import com.waymaps.contract.Callbacks;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AppLocalDataSource {


    private static final String LOG_TAG = AppLocalDataSource.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLocalDataSource.class);

    private static final Object LOCK = new Object();
    private static AppLocalDataSource sInstance;
    private final Context mContext;
    private final AppDatabase mAppDatabase;


    private final AppExecutors mExecutors;

    private AppLocalDataSource(Context context, AppExecutors executors, AppDatabase appDatabase) {
        mContext = context;
        mExecutors = executors;
        mAppDatabase = appDatabase;
    }

    public static AppLocalDataSource getInstance(Context context, AppExecutors executors, AppDatabase appDatabase) {
        LOGGER.debug(LOG_TAG, "Getting the local data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppLocalDataSource(context.getApplicationContext(), executors, appDatabase);
                LOGGER.debug(LOG_TAG, "Made new local data source");
            }
        }
        return sInstance;
    }

    public void getAllPhoneNumber(final Callbacks.Phones.LoadPhonesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<PhoneNumber> phones = mAppDatabase.phoneDao().getPhones();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!phones.isEmpty()) {
                            callback.onPhonesLoaded(phones);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void addPhoneNumber(final Callbacks.Phones.AddPhoneCallback callback, final PhoneNumber... phoneNumber) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.phoneDao().bulkInsert(phoneNumber);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void updatePhone(final Callbacks.Phones.EditPhoneCallback callback, final PhoneNumber phoneNumber){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.phoneDao().editPhone(phoneNumber);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void deletePhone(final Callbacks.Phones.DeletePhoneCallback callback, final String phoneNumber){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.phoneDao().deleteByPhoneNo(phoneNumber);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


}