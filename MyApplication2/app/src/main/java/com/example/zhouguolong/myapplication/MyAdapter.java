package com.example.zhouguolong.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by zhouguolong on 2017/5/3.
 */

public class MyAdapter extends BaseAdapter {
    private List<PhoneInfo> contacts;
    private Context context;
    private LinearLayout layout;

    public MyAdapter(Context context, List<PhoneInfo> contacts) {
        this.contacts = contacts;
        this.context = context;

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//		LayoutInflater inflater = LayoutInflater.from(context);
//		layout = (LinearLayout) inflater.inflate(R.layout.call, null);
//		TextView nametv = (TextView) layout.findViewById(R.id.name);
//		TextView numbertv = (TextView) layout.findViewById(R.id.number);
//		nametv.setText(lists.get(position).getName());
//		numbertv.setText(lists.get(position).getNumber());
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.call, null);
            holder = new ViewHolder();
            holder.nametv = (TextView) convertView.findViewById(R.id.name);
            holder.numbertv = (TextView) convertView.findViewById(R.id.number);
            holder.nametv.setText(contacts.get(position).getName());
            holder.numbertv.setText(contacts.get(position).getNumber());
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            holder.nametv.setText(contacts.get(position).getName());
            holder.numbertv.setText(contacts.get(position).getNumber());
        }
        return convertView;
    }
    private static class ViewHolder{
        TextView nametv;
        TextView numbertv;
    }
}
