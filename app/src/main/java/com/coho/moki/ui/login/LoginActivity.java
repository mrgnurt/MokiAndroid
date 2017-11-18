package com.coho.moki.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 27/09/2017.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.phone_number)
    EditText mEditTextPhoneNumber;

    @BindView(R.id.password)
    EditText mEditTextPassword;

    @BindView(R.id.login_button)
    Button mButtonLogin;

    @BindView(R.id.txtForgotPass)
    TextView txtForgotPass;

    @OnClick(R.id.login_button)
    public void onClickLoginButton(){
        checkLoginInput();
    }

    @OnClick(R.id.btnCancel)
    public void onClickCancelButton(){
        Utils.hideSoftKeyboard(this);
        openMainActivity();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void handleIntent(Intent intent) {
        int loginType = intent.getIntExtra(AppConstant.LOGIN_TYPE_TAG, 1);
        Log.d("tuton", "login type: " + loginType);
        if (loginType == AppConstant.OTHER_PEOPLE_LOGIN) {
            showPopup(AppConstant.LOGOUT_PUSH_CONTENT);
        }
    }


    @Override
    public void initView() {
        BaseApp.getActivityComponent().inject(this);
        mLoginPresenter.onAttach(this);
        txtForgotPass.setPaintFlags(txtForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtForgotPass.setText(R.string.forgot_pass);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void initData() {
    }

    @Override
    public void checkLoginInput() {

        String txtPhoneNumber = mEditTextPhoneNumber.getText().toString();
        String txtPassword = mEditTextPassword.getText().toString();

        if (txtPhoneNumber.equals("")){
            Utils.toastShort(LoginActivity.this, R.string.phone_number_empty);
        }
        else if (txtPassword.equals((""))){
            Utils.toastShort(LoginActivity.this, R.string.password_empty);
        }
        else{
            Utils.hideSoftKeyboard(this);
            if (Utils.checkInternetAvailable()){
                DialogUtil.showProgress(this);
                mLoginPresenter.requestLoginRemote(txtPhoneNumber, txtPassword);
            }
            else {
                DialogUtil.showPopupError(this, BaseApp.getContext().getString(R.string.error_msg_internet_not_connect));
            }

        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(BaseApp.getContext(), MainActivity.class);
        startActivity(intent);
        finishActivity();
    }

    public void finishActivity() {
        LoginActivity.this.finish();
    }

    public void showPopup(String content) {
        DialogUtil.showPopup(LoginActivity.this, content);
    }

//    BroadcastReceiver receiver;
//    public void registerLocalBroadcast() {
//        receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                try {
//                    String title= intent.getStringExtra(AppConstant.TITLE_PUSH_TAG);
//                    String content= intent.getStringExtra(AppConstant.CONTENT_PUSH_TAG);
//                    showPopup(title, content);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("com.coho.moki.push"));
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        registerLocalBroadcast();
//    }

}
