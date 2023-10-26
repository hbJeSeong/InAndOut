package com.jshwangbo.inandout.util;

import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Date;

public class UserStatus {
    public static final String TAG = "INO-UserStatus";
    private String sUserStatus;
    private boolean bIsRegistered;
    private DateUnit currentUpload;
    private DateUnit currentDownload;
    private DateUnit dateUnit;

    public UserStatus(String sUserStatus, boolean bIsRegistered){
        this.sUserStatus = sUserStatus;
        this.bIsRegistered = bIsRegistered;
        this.currentDownload = new DateUnit();
        this.currentUpload = new DateUnit();
        this.dateUnit = new DateUnit();
        Log.d(TAG, ":: new UserStatus Instance :: " + sUserStatus + " :: (registered : " + bIsRegistered + " )");
    }

    public UserStatus(){
        this(INOConstants.USER_STATUS_104, false);
    }

    public void setsUserStatus(String sUserStatus) {
        Log.d(TAG, ":: set UserStatus :: " + this.sUserStatus + " >> " + sUserStatus);
        this.sUserStatus = sUserStatus;
    }

    public void setbIsRegistered(boolean bIsRegistered){
        Log.d(TAG, ":: set Is Registered :: " + this.bIsRegistered + " >> " + bIsRegistered);
        this.bIsRegistered = bIsRegistered;
    }

    public void setDateUnit(String sTag) {
        if(!isValidTag(sTag))
            return;
        Log.d(TAG, " :: set DateUnit :: " + sTag);
        switch (sTag) {
            case INOConstants.TYPE_DATE_UNIT_COMMON:
                this.dateUnit = new DateUnit(new Date());
                break;
            case INOConstants.TYPE_DATE_UNIT_CURRENT_UPLOAD:
                this.currentUpload = new DateUnit(new Date());
                break;
            case INOConstants.TYPE_DATE_UNIT_CURRENT_DOWNLOAD:
                this.currentDownload = new DateUnit(new Date());
                break;
            default:
                Log.d(TAG, "Fail to Setting Date Unit :: Wrong TAG :: " + sTag);
                break;
        }
    }

    public DateUnit getCurrentDownload() {
        return this.currentDownload;
    }

    public DateUnit getCurrentUpload() {
        return this.currentUpload;
    }

    public DateUnit getDateUnit() {
        return this.dateUnit;
    }

    public String getUserStatus(){
        return this.sUserStatus;
    }

    private boolean isValidTag(final String sTag){
        return sTag.equals(INOConstants.TYPE_DATE_UNIT_CURRENT_UPLOAD) || sTag.equals(INOConstants.TYPE_DATE_UNIT_CURRENT_DOWNLOAD)
                || sTag.equals(INOConstants.TYPE_DATE_UNIT_COMMON);
    }

    public void printUserStatusInfo(){
        String tmp = String.format(
                        "==============================\n" +
                        "[ User Information ]\n" +
                        "+ TAG  : %s\n" +
                        "------------------------------\n" +
                        "[ Current Download ]\n" +
                        "+ DATE : %s\n" +
                        "+ TIME : %s\n" +
                        "------------------------------\n" +
                        "[ Current Upload ]\n" +
                        "+ DATE : %s\n" +
                        "+ TIME : %s\n" +
                        "------------------------------\n" +
                        "[ Date Unit ]\n" +
                        "+ Date : %s\n" +
                        "+ TIME : %s\n" +
                        "==============================",
                this.sUserStatus, this.currentDownload.sDate, this.currentDownload.sTime, this.currentUpload.sDate, this.currentUpload.sTime, this.dateUnit.sDate, this.dateUnit.sTime);
        Log.d(TAG, ":: Print User Status Information \n" + tmp);
    }

    public void action(final String str){
        /*
        * TODO
        * 출근 - 시작 로직
        * 출근 - 완료 로직
        * 퇴근 - 시작 로직
        * 퇴근 - 완료 로직
         */
    }
}

class DateUnit {
    public static final String TAG = "INO-DateUnit";
    public String sDate;
    public String sTime;

    public DateUnit(Date date) {
        String tmp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        this.sDate = tmp.substring(0, tmp.indexOf(" "));
        this.sTime = tmp.substring(tmp.indexOf(" ") + 1);
        Log.d(TAG, ":: Date Unit :: New Instance :: " + sDate + " | " + sTime);
    }

    public DateUnit(){
        this.sDate = null;
        this.sTime = null;
        Log.d(TAG, ":: Date Unit :: New Instance :: null");
    }

}