package com.laith.babylontest.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;
import com.laith.babylontest.network.ImageLoadUtil;
import com.laith.babylontest.viewmodel.PostClickListener;

import java.util.ArrayList;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder> {

    private final ArrayList<Post> posts;
    private final PostClickListener clickListener;
    private final DBHelper mDbHelper;
    private Context mContext;

    public PostsListAdapter(ArrayList<Post> posts, PostClickListener clickListener, DBHelper mDbHelper, Context context) {
        this.posts = posts;
        this.clickListener = clickListener;
        this.mDbHelper = mDbHelper;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtPostTitle.setText(posts.get(position).getTitle());
        User user = mDbHelper.getBriefUserInfo(posts.get(position).getUserId());
        if (user != null) {
            ImageLoadUtil.loadUserImage(user.getEmail(), mContext, holder.imgPosttAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView txtPostTitle;
        final ImageView imgPosttAvatar;


        ViewHolder(View v) {
            super(v);
            txtPostTitle = (TextView) v.findViewById(R.id.txt_post_item_title);
            imgPosttAvatar = (ImageView) v.findViewById(R.id.img_post_item_avatar);
            txtPostTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(posts.get(getLayoutPosition()));
        }
    }
}
