package com.waymaps.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.waymaps.broadcastReceiver.SmsReceiver;

public class SMSService extends Service {

    SmsReceiver smsReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

    public void processIncomingSMS(String sender,String text){
        System.out.println(sender + " " +text);
    }

    public void processIncomingGmail(){
        //mock
    }

    public void processServerRequests(){
        //mock
    }
}
