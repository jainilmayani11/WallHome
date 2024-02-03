package com.example.wallhome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallhome.models.ImageModel;


import java.io.IOException;
import java.util.Objects;

public class SetWallpaper extends AppCompatActivity {

    ImageModel imageModel;

    Intent intent;
    ImageView imageView,setBack;
    Button set;
    ImageButton download;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);


        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        setBack = findViewById(R.id.setBack);

        setBack.setOnClickListener(v -> onBackPressed());

        set = findViewById(R.id.set);
        download = findViewById(R.id.btnDownload);
        imageView = findViewById(R.id.finalImage);
        intent = getIntent();

        String url = intent.getStringExtra("image");
        Glide.with(getApplicationContext()).load(url).into(imageView);




        set.setOnClickListener(view -> {

            try{
                Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(SetWallpaper.this, "Wallpaper applied!", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e){
                e.printStackTrace();
                Toast.makeText(SetWallpaper.this, "Couldn't add Wallpaper!", Toast.LENGTH_SHORT).show();
            }

        });




        download.setOnClickListener(view -> {

            DownloadManager downloadManager;
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(imageModel.getSrc().getLarge());

            DownloadManager.Request request = new DownloadManager.Request(uri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle("Wallpaper_"+imageModel.getPhotographer())
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Wallpaper_"+imageModel.getPhotographer()+".jpg");

            downloadManager.enqueue(request);

            Toast.makeText(SetWallpaper.this, "Download Successfully!", Toast.LENGTH_SHORT).show();
        });




    }
}