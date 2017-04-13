package com.sahasu.lazypizza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by asd on 4/7/2017.
 */
public class MarketAdaptor extends RecyclerView.Adapter<MarketAdaptor.MyViewHolder> {

    private LayoutInflater inflator;
    static List<MarketInfo> data = Collections.emptyList();
    public static MarketAdaptor mk;

    public MarketAdaptor(Context context, List<MarketInfo> data)
    {
        this.data = data;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflator.inflate(R.layout.custom_order, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        mk =this;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        MarketInfo current = data.get(i);
        viewHolder.order_name.setText(current.order_name);
        viewHolder.address.setText(current.address);
        viewHolder.cost.setText(current.cost);
//        viewHolder.icon.setImageResource(current.img_id);
        viewHolder.ll.setBackgroundResource(current.img_id);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView order_name;
        TextView address;
        TextView cost;
        TextView details;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.menuIcon);
            details = (TextView) itemView.findViewById(R.id.viewDetails);
            order_name = (TextView) itemView.findViewById(R.id.orderName);
            address = (TextView) itemView.findViewById(R.id.address);
            cost = (TextView) itemView.findViewById(R.id.cost);

            details.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), "Clicked button at position : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(itemView.getContext(), MarketPlaceOrder.class);
            Bundle b = new Bundle();
            MarketInfo info = new MarketInfo();
            info = data.get(getAdapterPosition());
            intent.putExtra("orderName", info.order_name);
            intent.putExtra("placeBy", info.place_by);
            intent.putExtra("address", info.address);
            intent.putExtra("contact", info.phone_no);
            intent.putExtra("cost", info.cost);
            intent.putExtra("UID",info.UID);
            v.getContext().startActivity(intent);
        }
    }
    public void swapItems(List<MarketInfo> data)
    {
        mk.data = data;
        notifyDataSetChanged();
    }
}
