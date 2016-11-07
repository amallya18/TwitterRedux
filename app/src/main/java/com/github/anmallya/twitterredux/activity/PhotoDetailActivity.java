package com.github.anmallya.twitterredux.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

import com.github.anmallya.twitterclient.R;
import com.malmstein.fenster.controller.FensterPlayerController;
import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.controller.SimpleMediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

public class PhotoDetailActivity extends AppCompatActivity {

    FensterVideoView textureView;
    MediaFensterPlayerController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)){
                // Do stuff here
                setContentView(R.layout.activity_photo_detail);
                textureView = (FensterVideoView) findViewById(R.id.play_video_texture);
                playerController = (MediaFensterPlayerController) findViewById(R.id.play_video_controller);

                textureView.setMediaController(playerController);

                textureView.setVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                        SimpleMediaFensterPlayerController.DEFAULT_VIDEO_START);
                textureView.start();
            }
            else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
