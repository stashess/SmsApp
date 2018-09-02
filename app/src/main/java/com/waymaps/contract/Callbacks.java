package com.waymaps.contract;

import com.waymaps.data.model.Mail;
import com.waymaps.data.model.PhoneNumber;

import java.util.List;

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


}
