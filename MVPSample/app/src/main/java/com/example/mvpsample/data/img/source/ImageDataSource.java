package com.example.mvpsample.data.img.source;

import com.example.mvpsample.data.img.Image;

public interface ImageDataSource {

     interface imgCallback{

          void onMessage(String str);

          void getImage(Image image);
     }

     void uploadImage(Image image, imgCallback callback);

     void downloadImage(imgCallback callback);

     void deleteImage(imgCallback callback);
}
