package com.waymaps.util;

import android.telephony.SmsManager;

public class SMSHelper {

    public static void sendDebugSms(String number, String smsBody) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, smsBody, null, null);
    }
}