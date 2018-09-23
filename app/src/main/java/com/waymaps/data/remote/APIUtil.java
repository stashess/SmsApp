package com.waymaps.data.remote;

public class APIUtil {
    public static final String URL = "https://mob.waymaps.com/wm_mob_data.php/";

    public static final String DEFAULT_FORMAT = "json";


    public interface Action {
        String CALL = "call";
    }

    public interface Name {
        String SMS_SEND = "sms_send";
        String TRACKER_COMMAND_SEND = "tracker_command_send";
        String TRACKER_COMMAND_RECEIVE = "tracker_command_receive";
    }

}
