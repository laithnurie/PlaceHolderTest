package com.laith.babylontest.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.model.Comment;
import com.laith.babylontest.network.ImageLoadUtil;

import java.util.ArrayList;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Comment> comments;

    public CommentsListAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtCommentName.setText(comments.get(position).getName());
        holder.txtCommentBody.setText(comments.get(position).getBody());
        ImageLoadUtil.loadUserImage(comments.get(position).getEmail(), context,
                holder.imgCommentAvatar);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtCommentName;
        final TextView txtCommentBody;
        final ImageView imgCommentAvatar;

        ViewHolder(View v) {
            super(v);
            txtCommentName = (TextView) v.findViewById(R.id.txt_comment_item_name);
            txtCommentBody = (TextView) v.findViewById(R.id.txt_comment_item_body);
            imgCommentAvatar = (ImageView) v.findViewById(R.id.img_comment_item_avatar);
        }
    }
}
