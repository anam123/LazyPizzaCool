package com.sahasu.lazypizza;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
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
        viewHolder.order_name.setText(current.order_name + "| "+ current.src.toLowerCase());
        viewHolder.address.setText(current.address);
        viewHolder.cost.setText(current.cost);
        String hour = current.time_stamp.split("_")[1].substring(0,2);
        String minutes = current.time_stamp.split("_")[1].substring(2,4);
        String ts;
        if(Integer.parseInt(hour) < 12)
            ts = hour + ":" + minutes + " am";
        else
            ts = hour + ":" + minutes + " pm";
        viewHolder.timestamp.setText(ts);
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
        TextView timestamp;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.menuIcon);
            details = (TextView) itemView.findViewById(R.id.viewDetails);
            order_name = (TextView) itemView.findViewById(R.id.orderName);
            address = (TextView) itemView.findViewById(R.id.address);
            cost = (TextView) itemView.findViewById(R.id.cost);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            details.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            Toast.makeText(itemView.getContext(), "Clicked button at position : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            RelativeLayout linearLayout = new RelativeLayout(v.getContext());
            final NumberPicker aNumberPicker = new NumberPicker(v.getContext());
            aNumberPicker.setMaxValue(30);
            aNumberPicker.setMinValue(1);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
            RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            linearLayout.setLayoutParams(params);
            linearLayout.addView(aNumberPicker,numPicerParams);

            final AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
            alert.setTitle("Enter Potential Wait Time (In Mins)");
            alert.setView(linearLayout);
            final Intent intent = new Intent(itemView.getContext(), MarketPlaceOrder.class);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    String time=String.valueOf(aNumberPicker.getValue());
                    intent.putExtra("expected", time);
                    Bundle b = new Bundle();
                    MarketInfo info = new MarketInfo();
                    info = data.get(getAdapterPosition());
                    intent.putExtra("orderName", info.order_name);
                    intent.putExtra("placeBy", info.place_by);
                    intent.putExtra("address", info.address);
                    intent.putExtra("contact", info.phone_no);
                    intent.putExtra("source", info.src);
                    intent.putExtra("cost", info.cost);
                    intent.putExtra("UID",info.UID);
                    intent.putExtra("timestamp", info.time_stamp);
                    v.getContext().startActivity(intent);

                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            alert.show();



        }
    }
    public void swapItems(List<MarketInfo> data)
    {
        mk.data = data;
        notifyDataSetChanged();
    }
}
