package com.example.mvpsample.data.img.source;

import android.content.Context;

import com.example.mvpsample.data.img.Image;

public class ImageRepository implements ImageDataSource {

    private ImageRemoteDataSource imageRemoteDataSource;

    public ImageRepository(Context context) {
        imageRemoteDataSource = new ImageRemoteDataSource(context);
    }

    @Override
    public void uploadImage(Image image, imgCallback callback) {
        imageRemoteDataSource.uploadImage(image, new imgCallback() {
            @Override
            public void onMessage(String str) {
                callback.onMessage(str);
            }

            @Override
            public void getImage(Image image) {
            }
        });
    }

    @Override
    public void downloadImage(imgCallback callback) {
        imageRemoteDataSource.downloadImage(new imgCallback() {
            @Override
            public void onMessage(String str) {
            }

            @Override
            public void getImage(Image image) {
                callback.getImage(image);
            }
        });
    }

    @Override
    public void deleteImage(imgCallback callback) {
        imageRemoteDataSource.deleteImage(new imgCallback() {
            @Override
            public void onMessage(String str) {
                callback.onMessage(str);
            }

            @Override
            public void getImage(Image image) {

            }
        });
    }
}
