package com.jshwangbo.inandout.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUnit {
    public static final String TAG = INOConstants.DATEUNIT;
    public String sTag;
    public String sDate;
    public String sTime;

    public DateUnit(String sTag, Date date) {
        this.sTag = sTag;
        String tmp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        this.sDate = tmp.substring(0, tmp.indexOf(" "));
        this.sTime = tmp.substring(tmp.indexOf(" ") + 1);
        Log.d(TAG, ":: Date Unit :: New Instance :: " + sTag + " :: " + sDate + " | " + sTime);
    }

    public DateUnit(){
        this.sTag = null;
        this.sDate = null;
        this.sTime = null;
        Log.d(TAG, ":: Date Unit :: New Instance :: null");
    }

}