package com.example.mvpsample.contract;

import android.view.View;

public interface LoginContract {
    interface View {
        void showToast(String title);
    }
    interface Presenter {
        void setView(View view);
        void detachView();
        void doLogin(String id, String pw);
    }
}
