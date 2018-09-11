package com.waymaps;

import android.app.Application;

import com.waymaps.data.AppRepository;
import com.waymaps.data.local.pref.LocalPreferenceManager;
import com.waymaps.util.InjectorUtils;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class MainApplication extends Application {
    public static MainApplication INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    private void setDefaultSettings(){
        AppRepository appRepository = InjectorUtils.provideRepository(this);
        appRepository.setDefaultServiceStatus();
    }
}
