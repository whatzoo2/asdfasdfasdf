package com.gsh91.asdfasdfasdf;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CCC extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public CCC(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items=items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.recycler_item,parent,false);
        VH vh=new VH(itemView);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH)holder;

        Item item=items.get(position);

        vh.tvNO.setText(item.no+"");
        vh.tvDate.setText(item.date);
        vh.tvTitle.setText(item.title);
        vh.tvMsg.setText(item.msg);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvNO;
        TextView tvTitle;
        TextView tvMsg;
        TextView tvDate;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvNO=itemView.findViewById(R.id.tv_no);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvMsg=itemView.findViewById(R.id.tv_msg);
            tvDate=itemView.findViewById(R.id.tv_date);
        }
    }

}
