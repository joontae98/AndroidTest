package com.example.mvpsample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "LoginActivity";
    //binding 변수 생성
    ActivityLoginBinding binding;
    private LoginPresenter mPresenter;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding 객체 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLogin(this);
        mPresenter = new LoginPresenter();
        mPresenter.setView(this);
    }


    @Override
    public void showToast(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClick(View view) {
        Log.e(TAG, "start");
//        mPresenter.doLogin(binding.etxLoginId.getText().toString(), binding.etxLoginPw.getText().toString());
        String address = "http://192.168.0.113:4000/"+binding.etxLoginId.getText().toString();
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(address);
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
            Log.e(TAG,"end");
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