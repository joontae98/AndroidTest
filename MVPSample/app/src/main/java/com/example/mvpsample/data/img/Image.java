package com.example.mvpsample.data.img;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayOutputStream;

// 모델 코드
public class Image {

    private Bitmap bitmap;
    private String name;

    public Image(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
