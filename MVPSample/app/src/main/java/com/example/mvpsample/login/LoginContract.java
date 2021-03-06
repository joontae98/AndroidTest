package com.example.mvpsample.login;

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
