package com.waymaps.contract;

import com.waymaps.data.model.Mail;

import java.util.List;

public interface MailContract {

    interface MailView{

        void showMails(List<Mail> mails);

        void showMailDialog(Mail mail);

    }

    interface MailPresenter{
        void getAllMails();

        void addNewMail(Mail mail);

        void editMail(Mail mail);

        void deleteMail(Mail mail);

        void addMailButton();

        void editMailButton(Mail mail);
    }
}
