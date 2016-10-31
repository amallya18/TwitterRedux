package com.github.anmallya.twitterclient.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterclient.activity.DraftActivity;
import com.github.anmallya.twitterclient.activity.TweetListActivity;

/**
 * Created by anmallya on 10/29/2016.
 */

public class ComposeFragment extends DialogFragment {

    private EditText mEditText;

    public ComposeFragment() {
    }

    public static ComposeFragment newInstance(String profileImage) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString("profile", profileImage);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.et_compose);
        mEditText.requestFocus();

        ImageButton ib = (ImageButton) view.findViewById(R.id.ib_compose_profile);
        ImageButton ibCancel = (ImageButton) view.findViewById(R.id.ib_compose_cancel);
        ImageButton ibDrafts = (ImageButton) view.findViewById(R.id.ib_drafts);

        if(checkDraftExist()){
            System.out.println("############# draft exists");
            ibDrafts.setVisibility(View.VISIBLE);
            ibDrafts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DraftActivity.class);
                    getActivity().startActivityForResult(intent, 200);
                }
            });
        }else{
            System.out.println("############# draft doesnt exist");
        }

        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClicked();
            }
        });

        String profileUrl = getArguments().getString("profile", "Enter Name");
        Glide.with(getActivity()).load(profileUrl).into(ib);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final EditText etCompose = (EditText)view.findViewById(R.id.et_compose);
        final TextView tvWordCount = (TextView)view.findViewById(R.id.tv_word_count);

        etCompose.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int wordCount = 140 - etCompose.getText().toString().length();
                if(wordCount < 0){
                    tvWordCount.setTextColor(getResources().getColor(R.color.red));
                } else{
                    tvWordCount.setTextColor(getResources().getColor(R.color.black));
                }
                tvWordCount.setText(wordCount+"");
            }
        });

        Button tweetBtn = (Button)view.findViewById(R.id.btn_tweet);
        tweetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweet = mEditText.getText().toString();
                ((TweetListActivity)getActivity()).postTweet(tweet);
                dismiss();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    private void cancelClicked(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        saveDraft();
                        dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Save Draft?").setPositiveButton("Save", dialogClickListener)
                .setNegativeButton("Delete", dialogClickListener).show();
    }

    private void saveDraft() {
        System.out.println("Save draft called");
        SharedPreferences prefs = getActivity().getSharedPreferences("drafts", 0);
        SharedPreferences.Editor editor = prefs.edit();
        int size;
        if(prefs.contains("drafts" + "_size")){
            System.out.println("does contain");
            size = prefs.getInt("drafts" + "_size", 0);
            editor.putInt("drafts" +"_size", size+1);
        } else{
            System.out.println("doesnt contain");
            size = 0;
            editor.putInt("drafts" +"_size", 1);
        }
        editor.putString("drafts" + "_" + size, mEditText.getText().toString());
        editor.commit();
    }

    private boolean checkDraftExist(){
        SharedPreferences prefs = getActivity().getSharedPreferences("drafts", 0);
        if(prefs.contains("drafts" + "_size")){
            return true;
        }
        return false;
    }

    public void setDraft(String draft){
        mEditText.setText(draft);
    }


}