package com.waymaps.presenter;

import com.waymaps.contract.Callbacks;
import com.waymaps.contract.MailContract;
import com.waymaps.data.AppRepository;
import com.waymaps.data.model.Mail;
import com.waymaps.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MailPresenter implements MailContract.MailPresenter {

    private AppRepository appRepository;
    private AppExecutors appExecutors;
    private MailContract.MailView mailView;


    public MailPresenter(MailContract.MailView mailView, AppRepository appRepository,
                         AppExecutors appExecutors) {
        this.mailView = mailView;
        this.appRepository = appRepository;
        this.appExecutors = appExecutors;
    }

    @Override
    public void getAllMails() {
        appRepository.getAllMails(new Callbacks.Mails.LoadMailsCallback() {
            @Override
            public void onMailsLoaded(List<Mail> mails) {
                mailView.showMails(mails);
            }

            @Override
            public void onDataNotAvailable() {
                mailView.showMails(new ArrayList<Mail>());
            }
        });
    }

    @Override
    public void addNewMail(Mail mail) {
        appRepository.addMails(new Callbacks.Mails.AddMailCallback() {
            @Override
            public void onSuccess() {
                getAllMails();
            }

            @Override
            public void onError() {

            }
        }, mail);
    }

    @Override
    public void editMail(Mail mail) {
        appRepository.editMail(new Callbacks.Mails.EditMailCallback() {
            @Override
            public void onSuccess() {
                getAllMails();
            }

            @Override
            public void onError() {

            }
        }, mail);
    }

    @Override
    public void deleteMail(Mail mail) {
        appRepository.deleteMail(new Callbacks.Mails.DeleteMailCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        }, mail.getMail());
    }

    @Override
    public void checkConnection(final Callbacks.MailConnection.MailConnectionCallCheckConnection callback, Mail mail) {
        appRepository.checkConnection(new Callbacks.MailConnection.MailConnectionCallCheckConnection() {
            @Override
            public void onSuccess(String s) {
                callback.onSuccess(s);
                mailView.showToast(s);
            }
        },mail);
    }

    @Override
    public void addMailButton() {

    }

    @Override
    public void editMailButton(Mail mail) {

    }
}
