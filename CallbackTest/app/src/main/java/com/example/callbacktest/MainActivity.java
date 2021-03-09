package com.example.callbacktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sum total = new Sum();

        Sum.OnMaxNumberCb callback = new Sum.OnMaxNumberCb() {
            @Override
            public void onMaxNumber(int number, int exceed) {
                System.out.println("Current sum is " + number + " and exceeds " + exceed);
                try {
                    new Thread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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