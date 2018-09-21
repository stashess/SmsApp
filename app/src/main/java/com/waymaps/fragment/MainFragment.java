package com.waymaps.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.waymaps.MainApplication;
import com.waymaps.activity.MainActivity;
import com.waymaps.app.R;
import com.waymaps.contract.MainContract;
import com.waymaps.presenter.MainPresenter;
import com.waymaps.util.InjectorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends AbstractFragment implements MainContract.MainView{

    @BindView(R.id.start_service_button)
    Switch aSwitch;

    private MainContract.MainPresenter mainPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mainPresenter = new MainPresenter(this, InjectorUtils.provideRepository(getContext()));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainPresenter.onSwitchChange(isChecked);
                changeSwitchText(isChecked);
            }
        });
        return view;
    }

    @Override
    protected String getFragmentName() {
        return getResources().getString(R.string.main_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.getServiceStatus();
    }

    @Override
    public void showServiceStatus(boolean state) {
        aSwitch.setChecked(state);
        changeSwitchText(state);
    }

    @Override
    public void startService(Intent intent) {
        getActivity().startService(intent);
    }

    @Override
    public void stopService(Intent intent) {
        getActivity().stopService(intent);
    }

    private void changeSwitchText(boolean currentState){
        aSwitch.setText(currentState?getString(R.string.main_fragment_service_started):getString(R.string.main_fragment_service_stopped));
    }

}
