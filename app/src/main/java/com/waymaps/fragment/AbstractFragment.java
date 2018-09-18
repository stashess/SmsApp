package com.waymaps.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.ButterKnife;


public abstract class AbstractFragment extends Fragment {

    protected Logger logger;

    protected Object data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());
        getActivity().setTitle(getFragmentName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected void showProgress(final boolean show, final View... view) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        for (int i = 0; i < view.length; i++) {
            view[i].setVisibility(show ? View.GONE : View.VISIBLE);
        }

        view[0].animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for (int i = 0; i < view.length; i++) {
                    view[i].setVisibility(show ? View.GONE : View.VISIBLE);
                }
            }
        });
        view[0].animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for (int i = 0; i < view.length; i++) {
                    view[i].setVisibility(show ? View.GONE : View.VISIBLE);
                }
            }
        });

        view[1].setVisibility(show ? View.VISIBLE : View.GONE);
        view[1].animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view[1].setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }


    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(this.getClass());
        }
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    protected abstract String getFragmentName();

    public boolean onBackPressed() {
        return false;
    }

    public static AbstractFragment getNewInstance(Class<? extends AbstractFragment> abstractFragment, Object data){
        try {
            AbstractFragment aFragment = abstractFragment.newInstance();
            aFragment.setData(data);
            return aFragment;
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface FragmentName{
        String SCREEN_MAIN = "SCREEN_MAIN";
        String SCREEN_PHONE = "SCREEN_PHONE";
        String SCREEN_MAIL = "SCREEN_MAIL";
        String SCREEN_TASK = "SCREEN_TASK";
        String SCREEN_HOME = "SCREEN_HOME";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
