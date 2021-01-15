package com.example.doitmission03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    ScrollView scrollView1, scrollView2;
    ImageView imageView1, imageView2;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        scrollView2 = (ScrollView) findViewById(R.id.scrollView2);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        scrollView1.setHorizontalScrollBarEnabled(true);
        scrollView2.setHorizontalScrollBarEnabled(true);

//        Resources res = getResources();
//        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.image01);
//        int bitmapWidth = bitmap.getIntrinsicWidth();
//        int bitmapHeight = bitmap.getIntrinsicHeight();
//
//        imageView1.setImageDrawable(bitmap);
//        imageView1.getLayoutParams().width = bitmapWidth;
//        imageView1.getLayoutParams().height = bitmapHeight;
    }

    public void onButton1Clicked(View v) {
        changeImage(imageView1);
        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
    }
    public void onButton2Clicked(View v) {
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        changeImage(imageView2);
    }

    private void changeImage(ImageView image) {
        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.image01);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        image.setImageDrawable(bitmap);
        image.getLayoutParams().width = bitmapWidth;
        image.getLayoutParams().height = bitmapHeight;
    }
}