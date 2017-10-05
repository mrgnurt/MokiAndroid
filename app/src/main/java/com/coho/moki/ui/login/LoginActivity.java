package com.coho.moki.ui.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.Utils;

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

    @OnClick(R.id.login_button)
    public void onClickLoginButton(){
        checkLoginInput();
    }

    @OnClick(R.id.btnCancel)
    public void onClickCancelButton(){
        openMainActivity();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        BaseApp.getActivityComponent().inject(this);
        mLoginPresenter.onAttach(this);
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
            mLoginPresenter.requestLoginRemote(txtPhoneNumber, txtPassword);
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(BaseApp.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
