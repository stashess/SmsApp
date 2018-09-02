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
import com.waymaps.contract.PhoneContract;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPhoneDialog extends Dialog {

    private PhoneNumber mPhoneNumber;

    private PhoneContract.PhonePresenter mPhonePresenter;


    @BindView(R.id.button_save)
    Button saveButton;

    @BindView(R.id.editText_phoneNo)
    EditText editPhoneNo;

    protected AddPhoneDialog(@NonNull Context context) {
        super(context);
    }

    public AddPhoneDialog(Context context, PhoneNumber phoneNumber, PhoneContract.PhonePresenter phonePresenter){
        super(context);
        mPhoneNumber = phoneNumber;
        mPhonePresenter = phonePresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.phone_dialog);
        ButterKnife.bind(this);

        if (mPhoneNumber != null) {
            editPhoneNo.setText(mPhoneNumber.getPhoneNo());
        }

    }

    @OnClick(R.id.button_save)
    void onSaveClick(){
        bind();
        if (validate()) {
            mPhonePresenter.addNewPhone(mPhoneNumber);
            dismiss();
        }
    }

    private void bind() {
        if (mPhoneNumber == null){
            mPhoneNumber = new PhoneNumber();
        }
        mPhoneNumber.setPhoneNo(editPhoneNo.getText().toString());
    }

    private boolean validate() {
        String phoneNo = mPhoneNumber.getPhoneNo();
        if (phoneNo == null || !phoneNo.matches("^38\\d{10}$")){
            ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.toast_phoneNoDoesntMathc), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
