package com.example.viewpagertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    ViewPager vPager;
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = (ViewPager) findViewById(R.id.viewPager_main);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapterViewPager);

        indicator = (CircleIndicator) findViewById(R.id.indicator_main);
        indicator.setViewPager(vPager);
    }
}