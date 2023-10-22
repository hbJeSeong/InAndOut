package com.jshwangbo.inandout.util;

public interface Callbacks {
    public class cbArg {
        String str;
        String arg1;
        String arg2;

        public cbArg(String str){
            this(str, null, null);
        }

        public cbArg(String str, String arg){
            this(str, arg, null);
        }

        public cbArg(String str, String arg1, String arg2){
            this.str    = str;
            this.arg1   = arg1;
            this.arg2   = arg2;
        }
    }

    public void notifyEvt(String str, cbArg arg);
}
