package com.sahasu.lazypizza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MarketPlaceOrder extends AppCompatActivity {

    TextView order_name;
    TextView place_by;
    TextView contact_no;
    TextView cost_value;
    TextView add, timestamp;
    Button accept_order;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        context=getApplicationContext();
        if(getIntent() != null)
        {
            Intent in = getIntent();
            final String orderName = in.getStringExtra("orderName");
            final String placeby = in.getStringExtra("placeBy");
            final String address = in.getStringExtra("address");
            final String contact = in.getStringExtra("contact");
            final String cost = in.getStringExtra("cost");
            final String UID = in.getStringExtra("UID");
            final String time_stamp = in.getStringExtra("timestamp");
            String hour = time_stamp.split("_")[1].substring(0,2);
            String minutes = time_stamp.split("_")[1].substring(2,4);
            String ts;
            if(Integer.parseInt(hour) < 12)
                ts = hour + ":" + minutes + " am";
            else
                ts = hour + ":" + minutes + " pm";

            order_name = (TextView) findViewById(R.id.textView2);
            place_by = (TextView) findViewById(R.id.textView3);
            contact_no = (TextView) findViewById(R.id.textView8);
            cost_value = (TextView) findViewById(R.id.textView4);
            add = (TextView) findViewById(R.id.textView7);
            timestamp = (TextView) findViewById(R.id.timestamp);
            accept_order = (Button) findViewById(R.id.button);

            String f = "1 " + orderName;
            String s = "PLACED BY : " + placeby;
            String t = "ADDRESS : " + address;
            String fo = "CONTACT : " + contact;
            String fi = cost;
            String si = "TimeStamp : " + ts;

            order_name.setText(f);
            place_by.setText(s);
            contact_no.setText(fo);
            cost_value.setText(fi);
            add.setText(t);
            timestamp.setText(si);


            accept_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), UID, Toast.LENGTH_SHORT).show();
                    Log.d("ANOTHER EASY TAG", "/"+cost+"/");

                    data.acceptItemFromMarket(UID);
                    //data.orderCompleted(UID,placeby,data.email,cost.split(" ")[3]);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }



    public void onBackPressed()
    {
        finish();
    }
}
