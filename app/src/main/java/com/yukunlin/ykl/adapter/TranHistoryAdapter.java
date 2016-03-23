package com.yukunlin.ykl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.bean.History;
import com.yukunlin.ykl.database.TranHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukunlin on 16/3/23.
 */
public class TranHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<TranHistory> list;

    public TranHistoryAdapter(Context context, List<TranHistory> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tran_history, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.explain = (TextView) convertView.findViewById(R.id.explain);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TranHistory history = list.get(position);
        holder.title.setText(history.getContent());
        holder.explain.setText(history.getNote());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView explain;
    }
}
