package com.example.mvpsample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvpsample.R;
import com.example.mvpsample.contract.LoginContract;
import com.example.mvpsample.databinding.ActivityLoginBinding;
import com.example.mvpsample.presenter.LoginPresenter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    //binding 변수 생성
    ActivityLoginBinding binding;
    private LoginPresenter mPresenter;
    Bitmap bitmap;
//    private EditText etxId, etxPw;
//    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding 객체 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLogin(this);
        mPresenter = new LoginPresenter();
        mPresenter.setView(this);
    }

//    private void init() {
//        etxId = (EditText) findViewById(R.id.etx_login_id);
//        etxPw = (EditText) findViewById(R.id.etx_login_pw);
//        btnLogin = (Button) findViewById(R.id.btn_login);
//    }

    @Override
    public void showToast(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClick(View view) {
        mPresenter.doLogin(binding.etxLoginId.getText().toString(), binding.etxLoginPw.getText().toString());
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://i.imgur.com/JAkQFJj.png");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        mThread.start();
        try {
            mThread.join();
            binding.imgLogin.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}