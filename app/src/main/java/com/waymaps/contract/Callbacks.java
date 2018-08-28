package com.waymaps.contract;

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
}
