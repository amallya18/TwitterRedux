package com.github.anmallya.twitterredux.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.anmallya.twitterclient.R;


public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        String imageUrl = getIntent().getStringExtra("media");
        ImageView imageView = (ImageView) findViewById(R.id.iv_media);
        Glide.with(this).load(imageUrl).placeholder(R.color.grey).into(imageView);
    }

}
