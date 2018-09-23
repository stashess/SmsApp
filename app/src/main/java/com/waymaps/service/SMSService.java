package com.waymaps.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;

import com.waymaps.broadcastReceiver.SmsReceiver;
import com.waymaps.contract.Callbacks;
import com.waymaps.data.AppRepository;
import com.waymaps.data.model.Task;
import com.waymaps.jobscheduler.util.JobHandler;
import com.waymaps.util.InjectorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SMSService extends Service {

    SmsReceiver smsReceiver;

    private AppRepository appRepository;

    private static final String LOG_TAG = SMSService.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appRepository = InjectorUtils.provideRepository(getApplicationContext());

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsReceiver = new SmsReceiver();
        smsReceiver.setListener(new SmsReceiver.Listener() {
            @Override
            public void onTextReceived(String sender, String text) {
                processIncomingSMS(sender, text);
            }
        });
        registerReceiver(smsReceiver,filter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(smsReceiver);
        super.onDestroy();
    }

    public void processIncomingSMS(final String sender, final String text){
        Task task = new Task();
        task.setReceivedDate(new Date());
        task.setLastTryDate(new Date());
        task.setSender(sender);
        task.setStatus(Task.Statuses.TO_PROCESS);
        task.setText(text);
        task.setType(Task.Types.PHONE_TO_MAIL);
        appRepository.addTasks(new Callbacks.Tasks.AddTaskCallback() {
            @Override
            public void onSuccess() {
                deleteSMS(getApplicationContext(),text,sender);
            }

            @Override
            public void onError() {
                LOGGER.debug(LOG_TAG,"Something went wrong while trying to save SMS");
            }
        },task);
    }

    private void deleteSMS(Context context, String message, String number) {
        try {
            LOGGER.debug(LOG_TAG,"Deleting SMS from inbox");
            Uri uriSms = Uri.parse("content://sms/inbox");
            Cursor c = context.getContentResolver().query(uriSms,
                    new String[] { "_id", "thread_id", "address",
                            "person", "date", "body" }, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    if (message.equals(body) && address.equals(number)) {
                        LOGGER.debug(LOG_TAG,"Deleting SMS with id: " + threadId);
                        context.getContentResolver().delete(Uri.parse("content://sms/" + id), null, null);
                    }
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            LOGGER.debug(LOG_TAG,"Could not delete SMS from inbox: " + e.getMessage());
        }
    }

    public void processIncomingGmail(){
        //mock
    }

    public void processServerRequests(){
        //mock
    }
}
