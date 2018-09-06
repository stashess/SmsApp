package com.waymaps.data.remote;

import android.content.Context;

import com.waymaps.app.R;
import com.waymaps.conf.EmailPatternConfiguration;
import com.waymaps.data.model.Mail;
import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

public class GmailListener {

    private static final String LOG_TAG = GmailListener.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(GmailListener.class);

    private static final Object LOCK = new Object();
    private static GmailListener sInstance;
    private final Context mContext;


    private GmailListener(Context context) {
        mContext = context;
    }

    public static GmailListener getInstance(Context context) {
        LOGGER.debug(LOG_TAG, "Getting the gmail listener");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new GmailListener(context.getApplicationContext());
                LOGGER.debug(LOG_TAG, "Made new gmail listener");
            }
        }
        return sInstance;
    }


    public Message[] getNewGmails(Mail mail) throws MessagingException {

        Folder inbox = getFolder(mail);

        SearchTerm byDate = new ReceivedDateTerm(ComparisonTerm.GT, mail.getLastCheckDate());
        SearchTerm bySender = new SearchTerm() {
            @Override
            public boolean match(Message msg) {
                try {
                    return msg.getFrom()[0].toString().matches(EmailPatternConfiguration.SENDER);
                } catch (MessagingException e) {
                    return false;
                }
            }
        };

        SearchTerm bySubject = new SearchTerm() {
            @Override
            public boolean match(Message msg) {
                try {
                    return msg.getSubject().matches(EmailPatternConfiguration.SUBJECT);
                } catch (MessagingException e) {
                    return false;
                }
            }
        };

        SearchTerm[] searchTerms = new SearchTerm[3];
        searchTerms[0] = byDate;
        searchTerms[1] = bySender;
        searchTerms[2] = bySubject;

        SearchTerm searchTerm = new AndTerm(searchTerms);

        Message[] search = inbox.search(searchTerm);
        return search;
    }

    public String checkConnection(Mail mail){
        try {
            Folder inbox = getFolder(mail);
        } catch (MessagingException e) {
            return e.getMessage();
        }
        return mContext.getResources().getString(R.string.connection_successful);
    }

    private Folder getFolder(Mail mail) throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);
        Store store = session.getStore();
        store.connect("imap.gmail.com", mail.getMail(), mail.getPassword());
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        return inbox;
    }


}
