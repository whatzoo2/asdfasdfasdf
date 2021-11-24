package com.gsh91.asdfasdfasdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingAdapter extends BaseAdapter {

    Context context;
    ArrayList<MessageItem> messageItems;

    public ChattingAdapter(Context context, ArrayList<MessageItem> messageItems) {
        this.context = context;
        this.messageItems = messageItems;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageItem item=messageItems.get(position);

        View itemView=null;
        LayoutInflater inflater=LayoutInflater.from(context);

        if (item.name.equals(GG.nickName)) {
                itemView=inflater.inflate(R.layout.my_msgbox,parent,false);

            }else {
                itemView=inflater.inflate(R.layout.other_msgbox,parent,false);
            }

        CircleImageView civ= itemView.findViewById(R.id.iv);
        TextView tvName= itemView.findViewById(R.id.tv_name);
        TextView tvMsg= itemView.findViewById(R.id.tv_message);
        TextView tvTime= itemView.findViewById(R.id.tv_time);

        tvName.setText(item.name);
        tvMsg.setText(item.message);
        tvTime.setText(item.time);
        Glide.with(context).load(item.profileUrl).into(civ);



        return itemView;
    }
}
