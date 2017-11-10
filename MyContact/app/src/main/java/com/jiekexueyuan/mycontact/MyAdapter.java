package com.jiekexueyuan.mycontact;

/**
 * Created by stan on 2017/11/9.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class MyAdapter extends BaseAdapter {
    private List<PhoneInfo> contacts;
    private Context context;

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
