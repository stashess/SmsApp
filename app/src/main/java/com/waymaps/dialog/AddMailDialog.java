package com.waymaps.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.waymaps.app.R;
import com.waymaps.contract.MailContract;
import com.waymaps.data.model.Mail;
import com.waymaps.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMailDialog extends Dialog {

    private Mail mMail;

    private MailContract.MailPresenter mMailPresenter;

    @BindView(R.id.button_save)
    Button saveButton;

    @BindView(R.id.button_check_pass)
    Button checkPassButton;

    @BindView(R.id.editText_mail)
    EditText editMail;

    @BindView(R.id.editText_mail_pass)
    EditText editPass;

    protected AddMailDialog(@NonNull Context context) {
        super(context);
    }

    public AddMailDialog(Context context, Mail mail, MailContract.MailPresenter mailPresenter){
        super(context);
        mMailPresenter = mailPresenter;
        mMail = mail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mail_dialog);
        ButterKnife.bind(this);

        if (mMail != null) {
            editMail.setText(mMail.getMail());
            editPass.setText(mMail.getPassword());
        }
    }

    @OnClick(R.id.button_save)
    void onSaveClick(){
        bind();
        if (validate()) {
            mMailPresenter.addNewMail(mMail);
            dismiss();
        }
    }

    @OnClick(R.id.button_check_pass)
    void onCheckPass(){

    }

    private void bind() {
        if (mMail == null){
            mMail = new Mail();
        }
        mMail.setMail(editMail.getText().toString());
        mMail.setPassword(editPass.getText().toString());
    }

    private boolean validate() {
        if (mMail.getMail() == null || mMail.getMail().isEmpty()){
            ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.toast_mail_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mMail.getPassword() == null || mMail.getPassword().isEmpty()){
            ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.toast_pass_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
