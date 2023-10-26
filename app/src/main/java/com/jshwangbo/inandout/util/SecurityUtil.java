package com.jshwangbo.inandout.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.jshwangbo.inandout.activity.MainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private static String getUniqueValue(final Context context) {

        if (context == null){
            Log.d(TAG, ":: getUniqueValue :: \"FAILED\" = Context is Null");
            return null;
        }

        @SuppressLint("HardwareIds")
        String sUnique = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return sUnique;
    }

    public static String getSessionKey(final Context context) {
        String sNewSessionKey = "";
        StringBuffer stringBuffer;
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
            Log.d(TAG, ":: getSessionKey :: \"FAILED\" = Fail to get MessageDigest Instance");
            return null;
        }

        md.update(getUniqueValue(context).getBytes());
        stringBuffer = new StringBuffer();

        byte byteData[] = md.digest();
        for (int i=0; i<byteData.length; i++){
            stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        sNewSessionKey = stringBuffer.toString().toUpperCase();

        Log.d(TAG, ":: getSessionKey :: New Session Key = " + sNewSessionKey);

        return sNewSessionKey;
    }

//    public static String encrypt(){
//
//    }
}
