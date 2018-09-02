package com.waymaps.contract;

import android.view.View;

import com.waymaps.data.model.PhoneNumber;

import java.util.List;

public interface PhoneContract {


    public interface PhoneView {
        void showPhones(List<PhoneNumber> phoneNumbers);

        void showPhoneDialog(PhoneNumber phoneNumber);

    }

    public interface PhonePresenter {
        void getAllPhones();

        void addNewPhone(PhoneNumber phoneNumber);

        void editPhone(PhoneNumber phoneNumber);

        void deletePhone(PhoneNumber phoneNumber);

        void addPhoneButton();

        void editPhoneButton(PhoneNumber phoneNumber);
    }
}
