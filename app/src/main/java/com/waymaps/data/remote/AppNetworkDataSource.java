package com.waymaps.data.remote;

import android.content.Context;

import com.waymaps.contract.Callbacks;
import com.waymaps.data.model.Mail;
import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;

public class AppNetworkDataSource {

    // For Singleton instantiation

    private static final String LOG_TAG = AppNetworkDataSource.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppNetworkDataSource.class);

    private static final Object LOCK = new Object();
    private static AppNetworkDataSource sInstance;
    private final Context mContext;

    private final AppExecutors mExecutors;
    private final MailListener mMailListener;

    private AppNetworkDataSource(Context context, AppExecutors executors, MailListener mailListener) {
        mContext = context;
        mExecutors = executors;
        mMailListener = mailListener;
    }

    /**
     * Get the singleton for this class
     */
    public static AppNetworkDataSource getInstance(Context context, AppExecutors executors, MailListener mailListener) {
        LOGGER.debug(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppNetworkDataSource(context.getApplicationContext(), executors, mailListener);
                LOGGER.debug(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public void getMails(final Callbacks.Gmail.GmailCallGet callback, final Mail mail) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    final Message[] newGmails = mMailListener.getNewMessages(mail);

                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(newGmails);
                        }
                    });
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail();
                        }
                    });
                }

            }
        };
        mExecutors.networkIO().execute(runnable);
    }

    public void checkConnection(final Callbacks.Gmail.GmailCallCheckConnection callback, final Mail mail) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String s = mMailListener.checkConnection(mail);

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(s);
                    }
                });
            }
        };
        mExecutors.networkIO().execute(runnable);
    }

}
