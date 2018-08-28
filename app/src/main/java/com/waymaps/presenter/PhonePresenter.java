package com.waymaps.presenter;

import android.view.View;

import com.waymaps.contract.Callbacks;
import com.waymaps.contract.PhoneContract;
import com.waymaps.data.AppRepository;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.util.AppExecutors;

import java.util.List;

public class PhonePresenter implements PhoneContract.PhonePresenter {

    private AppRepository appRepository;
    private AppExecutors appExecutors;
    private PhoneContract.PhoneView phoneView;


    public PhonePresenter(PhoneContract.PhoneView phoneView, AppRepository appRepository,
                          AppExecutors appExecutors) {
        this.phoneView = phoneView;
        this.appRepository = appRepository;
        this.appExecutors = appExecutors;
    }

    @Override
    public void dropView() {

    }

    @Override
    public void takeView(View view) {

    }

    @Override
    public void getAllPhones() {
        appRepository.getAllPhoneNumber(new Callbacks.Phones.LoadPhonesCallback() {
            @Override
            public void onPhonesLoaded(List<PhoneNumber> phones) {
                phoneView.showPhones(phones);
            }

            @Override
            public void onDataNotAvailable() {
                phoneView.showPhones(null);
            }
        });
    }

    @Override
    public void addNewPhone(final PhoneNumber phoneNumber) {
        appRepository.addPhoneNumber(new Callbacks.Phones.AddPhoneCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        }, phoneNumber);
    }

    @Override
    public void editPhone(PhoneNumber phoneNumber) {
        appRepository.editPhone(new Callbacks.Phones.EditPhoneCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        }, phoneNumber);
    }

    @Override
    public void deletePhone(PhoneNumber phoneNumber) {
        appRepository.deletePhoneNumber(new Callbacks.Phones.DeletePhoneCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        }, phoneNumber.getPhoneNo());
    }

    @Override
    public void addPhoneButton() {

    }

    @Override
    public void editPhoneButton(PhoneNumber phoneNumber) {

    }
}
