package com.waymaps.contract;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public interface MainContract {
    interface MainView{
        void showServiceStatus(boolean state);
    }


    interface MainPresenter{
        void onSwitchChange(boolean state);

        void getServiceStatus();
    }
}
