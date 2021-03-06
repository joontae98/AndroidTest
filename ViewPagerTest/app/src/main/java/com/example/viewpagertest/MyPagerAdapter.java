package com.example.viewpagertest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.viewpagertest.Fragment.*;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // 인자 없이 인스턴스로 fragment 호출 코드
                FirstFragment f = new FirstFragment();
                return f;
//                return FirstFragment.newInstance(0, "Page # 1");
            case 1:
                SecondFragment s = new SecondFragment();
                return s;
//                return SecondFragment.newInstance(1, "Page # 2");
            case 2:
                ThirdFragment t = new ThirdFragment();
                return t;
//                return ThirdFragment.newInstance(2, "Page # 3");
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
