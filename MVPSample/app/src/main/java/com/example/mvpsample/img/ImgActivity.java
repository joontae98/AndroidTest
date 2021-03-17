package com.example.mvpsample.img;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mvpsample.R;
import com.example.mvpsample.data.img.Image;
import com.example.mvpsample.data.img.source.ImageRepository;
import com.example.mvpsample.databinding.ActivityImgBinding;

import java.io.IOException;

public class ImgActivity extends AppCompatActivity implements ImageContract.View {
    ActivityImgBinding binding;
    private Bitmap bitmap;
    private ImagePresenter presenter;
    private ImageRepository repository;
    private String name = "img21.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_img);
        binding.setImg(this);
        repository = new ImageRepository(this);
        presenter = new ImagePresenter(this, repository);

        binding.btnImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // presenter.imgupload()
                checkPermission();
            }
        });

        binding.btnImgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.downloadImage();
            }
        });

        binding.btnImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteImage();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    Image image = new Image(name, bitmap);
                    presenter.uploadImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
    @Override
    public void showImage(Image image) {
        binding.imgImg.setImageBitmap(image.getBitmap());
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str,Toast.LENGTH_SHORT).show();
    }
}
