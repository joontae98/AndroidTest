package com.example.mvpsample.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvpsample.R;
import com.example.mvpsample.contract.LoginContract;
import com.example.mvpsample.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter mPresenter;
    private EditText etxId, etxPw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        mPresenter = new LoginPresenter();
        mPresenter.setView(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doLogin(etxId.getText().toString(), etxPw.getText().toString());
            }
        });
    }

    private void init() {
        etxId = (EditText) findViewById(R.id.etx_login_id);
        etxPw = (EditText) findViewById(R.id.etx_login_pw);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void showToast(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}