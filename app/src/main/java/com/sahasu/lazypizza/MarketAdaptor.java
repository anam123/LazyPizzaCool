package com.sahasu.lazypizza;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * Created by asd on 4/7/2017.
 */
public class MarketAdaptor extends RecyclerView.Adapter<MarketAdaptor.MyViewHolder> {

    private LayoutInflater inflator;
    static List<MarketInfo> data1 = Collections.emptyList();
    public static MarketAdaptor mk;

    public MarketAdaptor(Context context, List<MarketInfo> data1)
    {
        this.data1 = data1;
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
        MarketInfo current = data1.get(i);
        viewHolder.order_name.setText(current.order_name);
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

    }





        @Override
    public int getItemCount() {

        return data1.size();
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
            Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(),"Arcon-Regular.otf");
            details.setTypeface(type);
            order_name.setTypeface(type);
            address.setTypeface(type);
            cost.setTypeface(type);
            timestamp.setTypeface(type);
            details.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
//            Toast.makeText(itemView.getContext(), "Clicked button at position : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            int count=0;
            for (int i = 0; i < com.sahasu.lazypizza.data.market.size(); i++) {
                if (com.sahasu.lazypizza.data.market.get(i).get("accepted").equals("1")) {
                    String x=data.email;
                    Log.d(x,"email");
                    Log.d(com.sahasu.lazypizza.data.market.get(i).get("deliveryboy"),"del");
                    if (data.stringToEmail(com.sahasu.lazypizza.data.market.get(i).get("deliveryboy")).equals(x)) {

                        count=1;
                        break;
                    }

                }
            }
            if(count==0){
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
                    info = data1.get(getAdapterPosition());
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
            alert.show();}

            else{

                Toast.makeText(itemView.getContext(), "You are already delivering an Order.", Toast.LENGTH_SHORT).show();
            }



        }
    }
    public void swapItems(List<MarketInfo> data1)
    {
        mk.data1 = data1;
        notifyDataSetChanged();
    }
}
