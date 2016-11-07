package com.github.anmallya.twitterredux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.activity.ProfileActivity;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.github.anmallya.twitterredux.utils.Consts;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by anmallya on 11/5/2016.
 */

public class FollowersAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> userList = null;
    private Context getContext() {
        return mContext;
    }
    private Context mContext;
    private TwitterClient client;

    public FollowersAdapter(Context context, List<User> userList, TwitterClient client) {
        this.userList = userList;
        this.mContext = context;
        this.client = client;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.layout_followers_holder, parent, false);
        viewHolder = new FollowerListViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FollowerListViewHolder vh1 = (FollowerListViewHolder) viewHolder;
        configureViewHolder(vh1, position);
    }

    private void setText(FollowerListViewHolder vh, User user){
        vh.getTvProfileName().setText(user.getName());
        vh.getTvProfileHandler().setText("@"+user.getScreenName());
        vh.getTvDesc().setText(user.getDescription());
    }

    private void setToggleButton(FollowerListViewHolder vh, User user){
        vh.getIvIsFriend().setChecked(user.isFollowing());
    }

    private void setImages(FollowerListViewHolder vh, final User user){
        ImageView ib = vh.getIvProfilePic();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                getContext().startActivity(intent);
            }
        });
        Glide.with(getContext()).load(user.getProfileImageUrl()).bitmapTransform(new RoundedCornersTransformation(getContext(), Consts.RS, Consts.RS)).placeholder(R.color.grey).into(ib);
    }

    private void setToggleListners(final FollowerListViewHolder vh,final  User user){
        (vh.getIvIsFriend()).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                user.setFollowing(!user.isFollowing());
            }
        });
    }

    private void configureViewHolder(final FollowerListViewHolder vh, int position) {
        final User user = userList.get(position);
        setText(vh, user);
        setToggleButton(vh, user);
        setImages(vh, user);
        setToggleListners(vh, user);
    }
}