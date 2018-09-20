package com.waymaps.contract;

import android.content.Context;
import android.content.Intent;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public interface MainContract {
    interface MainView{
        void showServiceStatus(boolean state);

        Context getContext();

        void startService(Intent intent);

        void stopService(Intent intent);
    }


    interface MainPresenter{
        void onSwitchChange(boolean state);

        void getServiceStatus();
    }
}
