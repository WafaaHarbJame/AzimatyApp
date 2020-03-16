package com.azimaty.azimatyapp.helper;

import android.util.Log;

public class IpLogger {

    private static final String TAG = "ImagePicker";

    private static com.esafirm.imagepicker.helper.IpLogger INSTANCE;

    private boolean isEnable = true;

    public static com.esafirm.imagepicker.helper.IpLogger getInstance() {
        if (INSTANCE == null) {
           // INSTANCE = new com.esafirm.imagepicker.helper.IpLogger();
        }
        return INSTANCE;
    }

    private IpLogger() {
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void d(String message) {
        if (isEnable) {
            Log.d(TAG, message);
        }
    }

    public void e(String message) {
        if (isEnable) {
            Log.e(TAG, message);
        }
    }

    public void w(String message) {
        if (isEnable) {
            Log.w(TAG, message);
        }
    }
}
