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

import com.waymaps.app.R;
import com.waymaps.contract.MailContract;
import com.waymaps.data.model.Mail;
import com.waymaps.dialog.AddMailDialog;
import com.waymaps.dialog.AddPhoneDialog;
import com.waymaps.presenter.MailPresenter;
import com.waymaps.ui.adapter.MyMailRecyclerViewAdapter;
import com.waymaps.ui.adapter.MyPhoneRecyclerViewAdapter;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;
import com.waymaps.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailFragment extends AbstractFragment implements MailContract.MailView {


    @BindView(R.id.mail_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.mail_add)
    FloatingActionButton addMail;


    private MailContract.MailPresenter mailPresenter;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Mail> mails = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        mailPresenter = new MailPresenter(this, InjectorUtils.provideRepository(getContext()), AppExecutors.getInstance());
        ButterKnife.bind(this,view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Mail mail = ((MyMailRecyclerViewAdapter) recyclerView.getAdapter()).getmValues().get(position);
                        showMailDialog(mail);
                    }
                });

        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        Mail mail = ((MyMailRecyclerViewAdapter) recyclerView.getAdapter()).getmValues().remove(position);
                        mailPresenter.deleteMail(mail);
                        recyclerView.getAdapter().notifyItemRemoved(position);
                        return true;
                    }
                });

        addMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMailDialog(null);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mailPresenter.getAllMails();
    }

    @Override
    public void showMails(List<Mail> mails) {
        mRecyclerView.swapAdapter(new MyMailRecyclerViewAdapter(mails), false);
    }

    @Override
    public void showMailDialog(Mail mail) {
        AddMailDialog addMailDialog = new AddMailDialog(getContext(),mail,mailPresenter);
        addMailDialog.show();
    }

    @Override
    protected String getFragmentName() {
        return getResources().getString(R.string.fragment_mail_name);
    }

}
