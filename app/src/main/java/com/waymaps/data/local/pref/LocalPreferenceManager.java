package com.waymaps.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalPreferenceManager {
    static final int INVALID_INT = -1;
    static final String INVALID_STRING = "";

    private LocalPreferenceManager() {
    }

    static void clearValue(Context context, String key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }

    static void setStringValue(Context context, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    static String getStringValue(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, INVALID_STRING);
    }

    static void setBooleanValue(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    static boolean getBooleanValue(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }

    private static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    private static void setDoubleValue(Context context, String key, double value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
    }

    private static Double getDoubleValue(Context context, String key) {
        String doubleValue = PreferenceManager.getDefaultSharedPreferences(context).getString(key, INVALID_STRING);
        if (INVALID_STRING.equals(doubleValue)) {
            return null;
        }
        return Double.valueOf(Double.parseDouble(doubleValue));
    }

    private static Float getFloatValue(Context context, String key) {
        String floatValue = PreferenceManager.getDefaultSharedPreferences(context).getString(key, INVALID_STRING);
        if (INVALID_STRING.equals(floatValue)) {
            return null;
        }
        return Float.valueOf(Float.parseFloat(floatValue));
    }

    private static void setIntValue(Context context, String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static int getIntValue(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, -1);
    }

    private static void setFloatValue(Context context, String key, float value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
    }

}
