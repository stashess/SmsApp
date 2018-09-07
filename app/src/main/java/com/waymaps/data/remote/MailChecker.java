package com.waymaps.data.remote;

import android.content.Context;

import com.waymaps.conf.EmailPatternConfiguration;
import com.waymaps.data.model.Mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

public class MailChecker {

    private static final String LOG_TAG = MailChecker.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(MailChecker.class);

    private static final Object LOCK = new Object();
    private static MailChecker sInstance;
    private final Context mContext;


    private MailChecker(Context context) {
        mContext = context;
    }

    public static MailChecker getInstance(Context context) {
        LOGGER.debug(LOG_TAG, "Getting the mail processor");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MailChecker(context.getApplicationContext());
                LOGGER.debug(LOG_TAG, "Made new mail processor");
            }
        }
        return sInstance;
    }

    public Message[] getNewMails(Folder inbox, Mail mail) throws MessagingException {
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
}
