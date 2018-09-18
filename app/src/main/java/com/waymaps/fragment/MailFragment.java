package com.waymaps.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.waymaps.app.R;
import com.waymaps.contract.MailContract;
import com.waymaps.data.model.Mail;
import com.waymaps.dialog.AddMailDialog;
import com.waymaps.dialog.AddPhoneDialog;
import com.waymaps.presenter.MailPresenter;
import com.waymaps.ui.adapter.MyMailRecyclerViewAdapter;
import com.waymaps.ui.adapter.MyPhoneRecyclerViewAdapter;
import com.waymaps.ui.adapter.MyViewHolder;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;
import com.waymaps.util.ItemClickSupport;
import com.waymaps.util.RecyclerItemTouchHelper;
import com.waymaps.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailFragment extends AbstractFragment implements MailContract.MailView {


    @BindView(R.id.mail_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.mail_main)
    RecyclerView mMainRecyclerView;


    @BindView(R.id.mail_add)
    FloatingActionButton addMail;


    private MailContract.MailPresenter mailPresenter;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManagerForMainMail;
    private List<Mail> mMails = new ArrayList<>();

    private Mail dummyMail = new Mail("test@example.com","12345678",new Date(),1,Mail.Statuses.NOT_CHECKED);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_mail, container, false);
        mailPresenter = new MailPresenter(this, InjectorUtils.provideRepository(getContext()), AppExecutors.getInstance());
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMainRecyclerView.setHasFixedSize(true);
        mLayoutManagerForMainMail = new LinearLayoutManager(getActivity().getApplicationContext());
        mMainRecyclerView.setLayoutManager(mLayoutManagerForMainMail);

        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Mail mail = ((MyMailRecyclerViewAdapter) recyclerView.getAdapter()).getmValues().get(position);
                        showMailDialog(mail);
                    }
                });

        ItemClickSupport.addTo(mMainRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Mail mail = ((MyMailRecyclerViewAdapter) recyclerView.getAdapter()).getmValues().get(position);
                        showMailDialog(mail);
                    }
                });

        /*ItemClickSupport.addTo(mRecyclerView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        Mail mail = ((MyMailRecyclerViewAdapter) recyclerView.getAdapter()).getmValues().remove(position);
                        mailPresenter.deleteMail(mail);
                        recyclerView.getAdapter().notifyItemRemoved(position);
                        return true;
                    }
                });
*/
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener recyclerItemTouchHelperListener = new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                if (viewHolder instanceof MyViewHolder) {
                    Mail mail = mMails.get(viewHolder.getAdapterPosition());
                    mMails.remove(mail);
                    mailPresenter.deleteMail(mail);
                    mRecyclerView.getAdapter().notifyItemRemoved(position);
                }
            }
        };

        ItemTouchHelper.SimpleCallback recyclerItemTouchHelper = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, recyclerItemTouchHelperListener);
        new ItemTouchHelper(recyclerItemTouchHelper).attachToRecyclerView(mRecyclerView);

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
        Mail mailWithPass = dummyMail;

        for (Mail mail : mails) {
            if (mail.getMainEmail() == 1) {
                mails.remove(mail);
                mailWithPass = mail;
                break;
            }
        }

        mMails = new ArrayList<>(mails);
        List<Mail> mainMail = new ArrayList<>();
        mainMail.add(mailWithPass);


        mRecyclerView.swapAdapter(new MyMailRecyclerViewAdapter(mMails), false);
        mMainRecyclerView.swapAdapter(new MyMailRecyclerViewAdapter(mainMail), false);
    }

    @Override
    public void showMailDialog(Mail mail) {
        boolean isMain = false;
        if (mail != null) {
            isMain = mail.getMainEmail() == 1;
        }
        AddMailDialog addMailDialog = new AddMailDialog(getContext(), mail,isMain, mailPresenter);
        addMailDialog.show();
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(getContext(),s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String getFragmentName() {
        return getResources().getString(R.string.fragment_mail_name);
    }

}
