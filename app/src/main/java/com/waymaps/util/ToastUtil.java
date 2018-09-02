package com.waymaps.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    public static Toast showToast(Context context, String text, int toastDuration ) {
        Toast toast = Toast.makeText(context,
                text,
                toastDuration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }
}
