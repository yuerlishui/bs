package com.yukunlin.ykl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.model.Comment;
import com.yukunlin.ykl.utils.SimpleUtils;

import java.util.List;

/**
 * Created by yukunlin on 16/3/28.
 */
public class RecommendAdapter extends BaseAdapter {
    private List<Comment> list;
    private Context context;

    public void setList(List<Comment> list) {
        this.list = list;
    }

    public RecommendAdapter(List<Comment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            holder.headImageView = (RoundedImageView) convertView.findViewById(R.id.headImageView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            holder.contentTextView = (TextView) convertView.findViewById(R.id.contentTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Comment comment = list.get(position);
        holder.nameTextView.setText(comment.getUserName());
        holder.contentTextView.setText(comment.getContent());
        if (comment.getDate() != null) {
            String data = SimpleUtils.getData(context, comment.getDate());
            holder.dateTextView.setText(data);
        }

        holder.headImageView.setImageResource(R.drawable.default_avatar_male);

        return convertView;
    }

    class ViewHolder {
        TextView nameTextView;
        RoundedImageView headImageView;
        TextView dateTextView;
        TextView contentTextView;
    }
}
