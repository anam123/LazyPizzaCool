package com.sahasu.lazypizza;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class Menu_PlaceOrder extends AppCompatActivity {

    TextView order_name;
    TextView place_by;
    TextView contact_no;
    TextView cost_value;
    TextView add;
    TextView rem;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__place_order);
        context=getApplicationContext();
        if(getIntent() != null)
        {
            Intent in = getIntent();
            final String orderName = in.getStringExtra("orderName");
            String placeby = data.name;
            final String address = in.getStringExtra("address");
            final String contact = data.phone;
            final String cost = in.getStringExtra("cost");
            final String source = in.getStringExtra("source");
            final String remarks = in.getStringExtra("remarks");
            final String scs = in.getStringExtra("scs");

            order_name = (TextView) findViewById(R.id.orderSummName);
            rem=(TextView) findViewById(R.id.remark);
            place_by = (TextView) findViewById(R.id.orderSummUsr);
            contact_no = (TextView) findViewById(R.id.orderSummContact);
            cost_value = (TextView) findViewById(R.id.orderSummCost);
            add = (TextView) findViewById(R.id.orderSummAdd);
            Button button = (Button) findViewById(R.id.orderSummButton);

            String f = orderName;
            String s = "PLACED BY : " + placeby;
            String t = "LOCATION : " + address;
            String fo = "CONTACT : " + contact;
            String fi = cost;
            String rm= "REMARKS : " + remarks;

            order_name.setText(f);
            place_by.setText(s);
            contact_no.setText(fo);
            cost_value.setText("Rs. "+fi+" + "+scs+" SC");
            add.setText(t);
            rem.setText(rm);

            button.setText("Place Order");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "ORDER PLACED", Toast.LENGTH_SHORT).show();
//                    data.orderCompleted(UID,placeby,data.email,cost.split(" ")[3]);
                    Log.d("LOGGING OUTSIDE" , "a");
                    data.addToMarket(orderName,scs,cost,remarks,address,source);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);

                    startActivity(intent);

                }
            });
        }
    }



    public void onBackPressed()
    {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
