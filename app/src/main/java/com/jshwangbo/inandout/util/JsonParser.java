package com.jshwangbo.inandout.util;

public class JsonParser {

    public static final String TAG = "INO-JsonParser";

    public static String parseForRequest(String reqType, String userID, String secretKey,
                                         String userStatus, String action, DateUnit userDate){
        String ret = null;

        if (reqType == null)
            reqType = "NA";

        if (userID == null)
            userID = "NA";

        if (secretKey == null)
            secretKey = "NA";

        if (userStatus == null)
            userStatus = "NA";

        if (action == null)
            action = "NA";

        ret = "{" +
                "\"header\": \"" + reqType + "\"," +
                "\"body\": {" +
                    "\"user\": \"" + userID + "\"," +
                    "\"key\": \"" + secretKey + "\"," +
                    "\"status\": " + userStatus + "\"," +
                    "\"data\" : {" +
                        "\"action\": " + action + "\"," +
                        "\"date\" : " + userDate.sDate + "\"" +
                        "\"time\" : " + userDate.sTime + "\"" +
                    "}" +
                "}" +
            "}";

        return ret;
    }

}
