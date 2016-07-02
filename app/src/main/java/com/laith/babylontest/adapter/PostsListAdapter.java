package com.laith.babylontest.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.viewmodel.PostClickListener;

import java.util.ArrayList;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private PostClickListener clickListener;

    public PostsListAdapter(ArrayList<Post> posts, PostClickListener clickListener) {
        this.posts = posts;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtPostTitle.setText(posts.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView txtPostTitle;

        ViewHolder(View v) {
            super(v);
            txtPostTitle = (TextView) v.findViewById(R.id.txt_post_item_title);
            txtPostTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(posts.get(getLayoutPosition()));
        }
    }
}
