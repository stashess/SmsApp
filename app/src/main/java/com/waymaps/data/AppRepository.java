package com.waymaps.data;

import com.waymaps.contract.Callbacks;
import com.waymaps.data.local.db.AppLocalDataSource;
import com.waymaps.data.model.Mail;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.data.remote.AppNetworkDataSource;
import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppRepository {

    private static final String LOG_TAG = AppRepository.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRepository.class);

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppRepository sInstance;
    private final AppNetworkDataSource mAppNetworkDataSource;
    private final AppLocalDataSource mAppLocalDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;


    private AppRepository(AppNetworkDataSource appNetworkDataSource,
                          AppLocalDataSource appLocalDataSource,
                          AppExecutors executors) {
        mAppNetworkDataSource = appNetworkDataSource;
        mExecutors = executors;
        mAppLocalDataSource = appLocalDataSource;
    }

    public synchronized static AppRepository getInstance(
            AppNetworkDataSource appNetworkDataSource,
            AppLocalDataSource appLocalDataSource,
            AppExecutors executors) {
        LOGGER.debug(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppRepository(appNetworkDataSource, appLocalDataSource,
                        executors);
                LOGGER.debug(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public void getAllPhoneNumber(Callbacks.Phones.LoadPhonesCallback loadPhonesCallback) {
        mAppLocalDataSource.getAllPhoneNumber(loadPhonesCallback);
    }

    public void addPhoneNumber(Callbacks.Phones.AddPhoneCallback callback, PhoneNumber... phoneNumber) {
        mAppLocalDataSource.addPhoneNumber(callback, phoneNumber);
    }

    public void deletePhoneNumber(Callbacks.Phones.DeletePhoneCallback callback, String phoneNumber) {
        mAppLocalDataSource.deletePhone(callback, phoneNumber);
    }

    public void editPhone(Callbacks.Phones.EditPhoneCallback callback, PhoneNumber phoneNumber) {
        mAppLocalDataSource.updatePhone(callback, phoneNumber);
    }

    public void getAllMails(Callbacks.Mails.LoadMailsCallback loadMailsCallback) {
        mAppLocalDataSource.getAllMails(loadMailsCallback);
    }

    public void addMails(Callbacks.Mails.AddMailCallback callback, Mail... mails) {
        mAppLocalDataSource.addMail(callback, mails);
    }

    public void deleteMail(Callbacks.Mails.DeleteMailCallback callback, String mail) {
        mAppLocalDataSource.deleteMail(callback, mail);
    }

    public void editMail(Callbacks.Mails.EditMailCallback callback, Mail mail) {
        mAppLocalDataSource.updateMail(callback, mail);
    }

    public void getEmails(Callbacks.Gmail.GmailCallGet callback, Mail mail) {
        mAppNetworkDataSource.getMails(callback, mail);
    }

    public void checkConnection(Callbacks.Gmail.GmailCallCheckConnection callback, Mail mail) {
        mAppNetworkDataSource.checkConnection(callback, mail);
    }



}
