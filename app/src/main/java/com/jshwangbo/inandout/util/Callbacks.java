package com.jshwangbo.inandout.util;

public interface MyCallback{
    public class myCb {
        String str;
        String arg1;
        String arg2;

        public myCb(String str){
            this(str, null, null);
        }

        public myCb(String str, String arg){
            this(str, arg, null);
        }

        public myCb(String str, String arg1, String arg2){
            this.str    = str;
            this.arg1   = arg1;
            this.arg2   = arg2;
        }
    }

    public void notifyEvt();
}
