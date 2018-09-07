package com.waymaps.data.remote;

import android.content.Context;

import com.waymaps.app.R;
import com.waymaps.conf.EmailPatternConfiguration;
import com.waymaps.data.model.Mail;
import com.waymaps.util.ProperiesHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by StanislavCheslavskyi on 07.09.2018.
 */

public class MailListener {

    private static final String LOG_TAG = MailListener.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(MailListener.class);

    private static final Object LOCK = new Object();
    private static MailListener sInstance;
    private final Context mContext;
    private final MailChecker mMailChecker;

    public static MailListener getInstance(Context context, MailChecker mailChecker) {
        LOGGER.debug(LOG_TAG, "Getting the mail listener");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MailListener(context.getApplicationContext(), mailChecker);
                LOGGER.debug(LOG_TAG, "Made new mail listener");
            }
        }
        return sInstance;
    }


    public Message[] getNewMessages(Mail mail) throws MessagingException {
        Properties properties = getPropertiesBasedOnMail(mail);
        Folder folder = getInboxFolder(mail, properties);
        return mMailChecker.getNewMails(folder,mail);
    }

    public String checkConnection(Mail mail) {
        try {
            Properties properties = getPropertiesBasedOnMail(mail);
            getInboxFolder(mail,properties);
        } catch (MessagingException e) {
            return e.getMessage();
        }
        return mContext.getResources().getString(R.string.connection_successful);
    }


    private Folder getInboxFolder(Mail mail, Properties properties) throws MessagingException {
        Session session = Session.getInstance(properties, null);
        Store store = session.getStore();
        store.connect((String) properties.get("host"), mail.getMail(), mail.getPassword());
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        return inbox;
    }

    private Properties getPropertiesBasedOnMail(Mail mail) throws MessagingException {
        if (mail.getMail().matches(EmailPatternConfiguration.GMAIL_PATTERN)) {
            return ProperiesHolder.getGmailProperties();
        } else {
            throw new MessagingException("Configuration to find such mail server not found");
        }
    }

    private MailListener(Context mContext, MailChecker mailChecker) {
        this.mContext = mContext;
        this.mMailChecker = mailChecker;
    }

}
