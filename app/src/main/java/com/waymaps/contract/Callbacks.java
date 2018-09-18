package com.waymaps.contract;

import com.waymaps.data.model.Mail;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.data.model.Task;

import java.util.List;

import javax.mail.Message;

public interface Callbacks {
    interface Phones {
        interface LoadPhonesCallback {
            void onPhonesLoaded(List<PhoneNumber> phones);

            void onDataNotAvailable();
        }

        interface EditPhoneCallback {
            void onSuccess();

            void onError();
        }

        interface AddPhoneCallback {
            void onSuccess();

            void onError();
        }

        interface DeletePhoneCallback {
            void onSuccess();

            void onError();
        }
    }

    interface Mails{
        interface LoadMailsCallback {
            void onMailsLoaded(List<Mail> mails);

            void onDataNotAvailable();
        }

        interface EditMailCallback {
            void onSuccess();

            void onError();
        }

        interface AddMailCallback {
            void onSuccess();

            void onError();
        }

        interface DeleteMailCallback {
            void onSuccess();

            void onError();
        }
    }

    interface MailConnection {
        interface MailConnectionCallGet {
            void onSuccess(Message[] messages);

            void onFail();
        }

        interface MailConnectionCallCheckConnection {
            void onSuccess(String s);
        }
    }

    interface Tasks{
        interface LoadTasksCallback {
            void onTasksLoaded(List<Task> task);

            void onDataNotAvailable();
        }

        interface EditTaskCallback {
            void onSuccess();

            void onError();
        }

        interface AddTaskCallback {
            void onSuccess();

            void onError();
        }

        interface DeleteTaskCallback {
            void onSuccess();

            void onError();
        }
    }

}
