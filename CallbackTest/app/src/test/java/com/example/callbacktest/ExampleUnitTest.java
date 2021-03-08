package com.example.callbacktest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Sum total = new Sum();

        Sum.OnMaxNumberCb callback = new Sum.OnMaxNumberCb() {
            @Override
            public void onMaxNumber(int number, int exceed) {
                System.out.println("Current sum is " + number + " and exceeds " + exceed);
            }
        };

        total.setMaxNumber(50);
        total.setOnMaxNumberCb(callback);

        for (int i = 1; i<=11; i++){
            total.addNumber(i);
        }
        System.out.println("Total is "+ total.getTotal());
    }
}