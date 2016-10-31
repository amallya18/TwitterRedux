package com.github.anmallya.twitterclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterclient.fragments.ComposeFragment;

/**
 * Created by anmallya on 10/30/2016.
 */

public class IntentDataActivity extends AppCompatActivity {
    private EditText mEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_compose);
        mEditText = (EditText) findViewById(R.id.et_compose);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String titleOfPage = intent.getStringExtra(Intent.EXTRA_SUBJECT);
                String urlOfPage = intent.getStringExtra(Intent.EXTRA_TEXT);
                Uri imageUriOfPage = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                System.out.println("data recieved");
                mEditText.setText(urlOfPage);
            }
        }
    }
}
