package com.example.mvpsample.img;

import com.example.mvpsample.data.img.Image;
import com.example.mvpsample.data.img.source.ImageDataSource;
import com.example.mvpsample.data.img.source.ImageRepository;

public class ImagePresenter implements ImageContract.Presenter{

    private ImageContract.View view;
    private ImageRepository repository;

    public ImagePresenter(ImageContract.View view, ImageRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void uploadImage(Image image) {
        view.showImage(image);
        repository.uploadImage(image, new ImageDataSource.imgCallback() {
            @Override
            public void onMessage(String str) {
                view.showToast(str);
            }
            @Override
            public void getImage(Image image) {
            }
        });
    }

    @Override
    public void downloadImage() {
        repository.downloadImage(new ImageDataSource.imgCallback() {
            @Override
            public void onMessage(String str) {
            }

            @Override
            public void getImage(Image image) {
                view.showImage(image);
            }
        });

    }

}
