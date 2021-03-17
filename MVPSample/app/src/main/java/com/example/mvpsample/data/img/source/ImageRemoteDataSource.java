package com.example.mvpsample.data.img.source;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mvpsample.VolleyMultipartRequest;
import com.example.mvpsample.data.img.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageRemoteDataSource implements ImageDataSource {

    private Context context;
    private Bitmap bitmap;

    public ImageRemoteDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void uploadImage(Image image, imgCallback callback) {
        String address = "http://192.168.0.113:3232/process/test";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, address,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onMessage("success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart(image.getName(),getFileDataFromDrawable(image.getBitmap())));
                return params;
            }
        };
        volleyMultipartRequest.setShouldCache(false);
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    @Override
    public void downloadImage(imgCallback callback) {
        String name = "img21.jpg";
        String address = "http://192.168.0.113:3232/process/"+name;
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        mThread.start();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Image image = new Image(name, bitmap);
        callback.getImage(image);
    }

    @Override
    public void deleteImage(imgCallback callback) {
        String address = "http://192.168.0.113:3232/process/Delete/img21.jpg";
        StringRequest request = new StringRequest(Request.Method.POST, address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    callback.onMessage(object.getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("GotError",""+error.getMessage());
            }
        });
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}