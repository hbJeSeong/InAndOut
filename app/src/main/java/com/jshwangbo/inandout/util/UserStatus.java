package com.jshwangbo.inandout.util;

import android.util.Log;


import java.util.Date;

public class UserStatus {
    public static final String TAG = INOConstants.USERSTATUS;
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
        this(null, false);
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
            case INOConstants.TAG_DATE_UNIT_COMMON:
                this.dateUnit = new DateUnit(sTag, new Date());
                break;
            case INOConstants.TAG_DATE_UNIT_CURRENT_UPLOAD:
                this.currentUpload = new DateUnit(sTag, new Date());
                break;
            case INOConstants.TAG_DATE_UNIT_CURRENT_DOWNLOAD:
                this.currentDownload = new DateUnit(sTag, new Date());
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

    private boolean isValidTag(final String sTag){
        return sTag.equals(INOConstants.TAG_DATE_UNIT_CURRENT_UPLOAD) || sTag.equals(INOConstants.TAG_DATE_UNIT_CURRENT_DOWNLOAD)
                || sTag.equals(INOConstants.TAG_DATE_UNIT_COMMON);
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
                        "+ TAG  : %s\n" +
                        "+ Date : %s\n" +
                        "+ TIME : %s\n" +
                        "==============================",
                this.sUserStatus, this.currentDownload.sDate, this.currentDownload.sTime, this.currentUpload.sDate, this.currentUpload.sTime, this.dateUnit.sTag, this.dateUnit.sDate, this.dateUnit.sTime);
        Log.d(TAG, ":: Print User Status Information \n" + tmp);
    }
}
