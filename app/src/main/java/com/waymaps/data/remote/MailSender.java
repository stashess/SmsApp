package com.waymaps.data.remote;

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

    private static final String LOG_TAG = MailSender.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

    private static final Object LOCK = new Object();
    private static MailSender sInstance;
    private final Context mContext;

    public MailSender(Context mContext) {
        this.mContext = mContext;
    }

    public static MailSender getInstance(Context context) {
        LOGGER.debug(LOG_TAG, "Getting the mail sender");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MailSender(context.getApplicationContext());
                LOGGER.debug(LOG_TAG, "Made new mail sender");
            }
        }
        return sInstance;
    }




}
