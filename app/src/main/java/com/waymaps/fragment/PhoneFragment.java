package com.waymaps.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.waymaps.app.R;
import com.waymaps.contract.PhoneContract;
import com.waymaps.data.AppRepository;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.presenter.PhonePresenter;
import com.waymaps.ui.adapter.MyPhoneRecyclerViewAdapter;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;
import com.waymaps.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneFragment extends AbstractFragment implements PhoneContract.PhoneView {


    @BindView(R.id.phone_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.phone_add)
    FloatingActionButton addPhone;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    private PhoneContract.PhonePresenter phonePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        ButterKnife.bind(this, view);
        phonePresenter = new PhonePresenter(this,InjectorUtils.provideRepository(getContext()),AppExecutors.getInstance());

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        PhoneNumber phoneNumber = ((MyPhoneRecyclerViewAdapter) recyclerView.getAdapter()).getmValues().get(position);
                        phonePresenter.editPhone(phoneNumber);
                    }
                });

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonePresenter.addPhoneButton();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        phonePresenter.getAllPhones();
    }

    @Override
    public void showPhones(List<PhoneNumber> list) {
        mRecyclerView.swapAdapter(new MyPhoneRecyclerViewAdapter(list), false);
    }

    @Override
    public void showPhoneDialog(PhoneNumber phoneNumber) {

    }

    @Override
    protected String getFragmentName() {
        return getResources().getString(R.string.fragment_phone_name);
    }
}
