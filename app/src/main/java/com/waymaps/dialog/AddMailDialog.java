package com.waymaps.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.waymaps.app.R;
import com.waymaps.contract.Callbacks;
import com.waymaps.contract.MailContract;
import com.waymaps.data.model.Mail;
import com.waymaps.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMailDialog extends Dialog {

    private Mail mMail;

    private Mail mVerifiedMail;

    private boolean mMainMail;

    private MailContract.MailPresenter mMailPresenter;

    @BindView(R.id.button_save)
    Button saveButton;

    @BindView(R.id.button_check_pass)
    Button checkPassButton;

    @BindView(R.id.editText_mail)
    EditText editMail;

    @BindView(R.id.editText_mail_pass)
    EditText editPass;

    @BindView(R.id.password_view)
    View passwordView;

    protected AddMailDialog(@NonNull Context context) {
        super(context);
    }

    public AddMailDialog(Context context, Mail mail, boolean mainEmail, MailContract.MailPresenter mailPresenter) {
        super(context);
        mMailPresenter = mailPresenter;
        mMail = mail;
        mMainMail = mainEmail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mail_dialog);
        ButterKnife.bind(this);

        if (!mMainMail){
            checkPassButton.setVisibility(View.GONE);
            editPass.setVisibility(View.GONE);
            passwordView.setVisibility(View.GONE);
        }

        if (mMail != null) {
            editMail.setText(mMail.getMail());
            editPass.setText(mMail.getPassword());
        }
    }

    @OnClick(R.id.button_save)
    void onSaveClick() {
        bind();
        if (validate()) {
            if (mMainMail){
                if (mVerifiedMail != null && mMail.getMail().equals(mVerifiedMail.getMail()) && mMail.getPassword().equals(mVerifiedMail.getPassword())){
                    mMail.setStatus(mVerifiedMail.getStatus());
                } else {
                    mMail.setStatus(0);
                }
            }
            mMailPresenter.addNewMail(mMail);
            dismiss();
        }
    }

    @OnClick(R.id.button_check_pass)
    void onCheckPass() {
        bind();
        if (validate()) {
            mMailPresenter.checkConnection(new Callbacks.MailConnection.MailConnectionCallCheckConnection() {
                @Override
                public void onSuccess(String s) {
                    if (getContext().getResources().getString(R.string.connection_successful).equals(s)){
                        mMail.setStatus(1);
                    } else {
                        mMail.setStatus(2);
                    }
                    mVerifiedMail = mMail;
                }
            },mMail);
        }
    }

    private void bind() {
        if (mMail == null) {
            mMail = new Mail();
        }
        mMail.setMail(editMail.getText().toString());
        mMail.setPassword(editPass.getText().toString());
        mMail.setMainEmail(mMainMail?1:0);
    }

    private boolean validate() {
        if (mMail.getMail() == null || mMail.getMail().isEmpty()) {
            ToastUtil.showToast(getContext(), getContext().getResources().getString(R.string.toast_mail_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mMainMail) {
            if (mMail.getPassword() == null || mMail.getPassword().isEmpty()) {
                ToastUtil.showToast(getContext(), getContext().getResources().getString(R.string.toast_pass_empty), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
