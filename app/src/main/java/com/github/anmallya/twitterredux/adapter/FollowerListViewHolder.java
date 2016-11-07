package com.github.anmallya.twitterredux.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.anmallya.twitterclient.R;

/**
 * Created by anmallya on 11/5/2016.
 */

public class FollowerListViewHolder extends RecyclerView.ViewHolder {

    private TextView tvProfileName, tvProfileHandler;

    public ToggleButton getIvIsFriend() {
        return ivIsFriend;
    }

    public void setIvIsFriend(ToggleButton ivIsFriend) {
        this.ivIsFriend = ivIsFriend;
    }

    private ToggleButton ivIsFriend;
    private ImageView ivProfilePic;
    private TextView tvDesc;

    public TextView getTvDesc() {
        return tvDesc;
    }

    public void setTvDesc(TextView tvDesc) {
        this.tvDesc = tvDesc;
    }


    public void setIvProfilePic(ImageView ivProfilePic) {
        this.ivProfilePic = ivProfilePic;
    }

    public ImageView getIvProfilePic() {
        return ivProfilePic;
    }

    public void setIvProfilePic(ImageButton ivProfilePic) {
        this.ivProfilePic = ivProfilePic;
    }

    public TextView getTvProfileName() {
        return tvProfileName;
    }

    public void setTvProfileName(TextView tvProfileName) {
        this.tvProfileName = tvProfileName;
    }

    public TextView getTvProfileHandler() {
        return tvProfileHandler;
    }

    public void setTvProfileHandler(TextView tvProfileHandler) {
        this.tvProfileHandler = tvProfileHandler;
    }

    public FollowerListViewHolder(View v) {
        super(v);
        setTvProfileHandler((TextView)v.findViewById(R.id.tv_profile_handle));
        setTvProfileName((TextView)v.findViewById(R.id.tv_profile_name));
        setTvDesc((TextView)v.findViewById(R.id.tv_desc));
        setIvIsFriend((ToggleButton)v.findViewById(R.id.ib_add_friend));
        setIvProfilePic((ImageView)v.findViewById(R.id.iv_profile_pic));
    }

}
