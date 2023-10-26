package com.jshwangbo.inandout.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityUtil {
    public static final String TAG = "INO-SecurityUtil";

    public static boolean isValidPassword(final String sPassword){
        boolean bCheckCharacter = false;
        boolean bCheckLength    = false;

        String sPasswordPattern = "^[!@#\\$%\\^?A-Za-z]+$";
        Pattern pattern = Pattern.compile(sPasswordPattern);
        Matcher matcher = pattern.matcher(sPassword);
        bCheckCharacter = matcher.matches();

        int iPasswordLength = sPassword.length();
        if (iPasswordLength >= 8 && iPasswordLength <= 16)
            bCheckLength = true;
        else
            bCheckLength = false;


        if (!bCheckCharacter)
            Log.d(TAG, ":: isValidPassword :: Password contains invalid character");
        if (!bCheckLength)
            Log.d(TAG, ":: isValidPassword :: Password's Length is invalid \"LEN : " + iPasswordLength + "\"");
        if (bCheckCharacter && bCheckLength)
            Log.d(TAG, ":: isValidPassword :: Password is valid");

        return bCheckCharacter && bCheckLength;
    }

    public static boolean isPasswordCorrect(final String arg1, final String arg2){
        boolean result = false;

        if (!arg1.equals(arg2)) {
            Log.d(TAG, ":: isPasswordCorrect :: Password is not same");
            result = false;
        } else if (!isValidPassword(arg1)) {
            Log.d(TAG, ":: isPasswordCorrect :: Password is not valid");
            result = false;
        } else {
            Log.d(TAG, ":: isPasswordCorrect :: Password is valid and same");
            result = true;
        }

        return result;
    }
}
