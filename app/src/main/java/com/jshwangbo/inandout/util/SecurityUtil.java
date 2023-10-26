package com.jshwangbo.inandout.util;

import android.util.Log;

public class SecurityUtil {
    public static final String TAG = "INO-SecurityUtil";
    public static SecurityUtil instance = null;

    public static SecurityUtil getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new SecurityUtil();
        }
    }

    public SecurityUtil() {

    }

    public static boolean isValidPassword(final String str){
        boolean result = false;
        String ret      = null;
        int count       = 0;

        if (str.contains(" ")) {
            ret = INOConstants.PASSWORD_ERROR_INVALID_SPACE;
            count++;
        }
        if (str.contains("\"") || str.contains("\'") || str.contains(">") || str.contains("<") || str.contains("/")
                || str.contains("|") || str.contains("\\") || str.contains("+") || str.contains("=")) {
            ret = INOConstants.PASSWORD_ERROR_INVALID_WRONG_CHAR;
            count++;
        }
        if (str.length() < 8) {
            ret = INOConstants.PASSWORD_ERROR_INVALID_LENGTH_SHORT;
            count++;
        } else if (str.length() > 16) {
            ret = INOConstants.PASSWORD_ERROR_INVALID_LENGTH_LONG;
            count++;
        }

        switch (count){
            case 0:
                result = true;
                break;
            case 1:
                result = false;
                break;
            default:
                result = false;
                ret = INOConstants.PASSWORD_ERROR_INVALID;
                break;
        }
        Log.d(TAG, ":: isValidPassword :: " + ret + ((count >= 1) ? " :: INVALID_PROPERTY = " + count : ""));
        return result;
    }
}
