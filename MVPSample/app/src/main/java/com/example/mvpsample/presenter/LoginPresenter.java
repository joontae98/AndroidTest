package com.example.mvpsample.presenter;

import com.example.mvpsample.contract.LoginContract;
import com.example.mvpsample.model.User;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private User user;

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void doLogin(String id, String pw) {
     this.user = new User(id, pw);
        user.findUser(user, new User.Callback() {
            @Override
            public void onSuccess() {
                view.showToast("Login Success");
//                System.out.println("Login Success");
            }

            @Override
            public void onFail() {
                view.showToast("Login Fail");
//                System.out.println("Login Fail");
            }
        });
    }
}
