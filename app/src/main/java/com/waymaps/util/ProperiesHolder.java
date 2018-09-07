package com.waymaps.util;

import java.util.Properties;

/**
 * Created by StanislavCheslavskyi on 07.09.2018.
 */

public class ProperiesHolder {
    private static Properties gmailProperties;

    static{
        gmailProperties = new Properties();
        gmailProperties.setProperty("mail.store.protocol", "imaps");
        gmailProperties.setProperty("host","imap.gmail.com");
    }

    public static Properties getGmailProperties() {
        return gmailProperties;
    }
}
