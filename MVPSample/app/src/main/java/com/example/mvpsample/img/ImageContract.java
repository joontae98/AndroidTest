package com.example.mvpsample.img;

import com.example.mvpsample.data.img.Image;

public interface ImageContract {
    interface View {

        void showImage(Image image);

        void showToast(String str);
    }

    interface Presenter {

        void uploadImage(Image image);

        void downloadImage();

    }
}
