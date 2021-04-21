package com.example.customscrollviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    private Drawable mActionBarBackgroundDrawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.ic_launcher_background);
        mActionBarBackgroundDrawable.setAlpha(0);

        getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

        ((CustomScrollView) findViewById(R.id.scroll_view)).setScrollViewListener(mOnScrollChangedListener);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        }
    }

    private ScrollViewListener mOnScrollChangedListener = new ScrollViewListener() {
        @Override
        public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
            final int headerHeight = findViewById(R.id.img_header).getHeight() - getActionBar().getHeight();
            final float ratio = (float) Math.min(Math.max(y, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };
    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };

}