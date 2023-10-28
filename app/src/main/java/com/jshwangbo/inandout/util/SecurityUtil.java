package com.jshwangbo.inandout.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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

    public static String encrypt(final String sPlainText, Context context) {
        final String ALGORITHM = "AES/CBC/PKCS5Padding";
        String secretKey = getSessionKey(context);
        String iv = secretKey.substring(0, 16);

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encryptd = cipher.doFinal(sPlainText.getBytes("UTF-8"));
            String ret = Base64.getEncoder().encodeToString(encryptd);

            Log.d(TAG, ":: encrypt :: RESULT = " + ret);

            return ret;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException |
                 IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(final String sCipher, Context context) {
        final String ALGORITHM = "AES/CBC/PKCS5Padding";
        String secretKey = getSessionKey(context);
        String iv = secretKey.substring(0, 16);

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decodedText = Base64.getDecoder().decode(sCipher);
            byte[] decrypted = cipher.doFinal(decodedText);

            String ret = new String(decrypted, "UTF-8");

            Log.d(TAG, ":: decrypt :: RESULT = " + ret);

            return ret;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
