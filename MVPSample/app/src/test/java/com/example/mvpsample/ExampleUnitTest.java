package com.example.mvpsample;

import com.example.mvpsample.login.LoginPresenter;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LoginPresenter mPresenter = new LoginPresenter();
        mPresenter.doLogin("1@1.1","2");

    }
}