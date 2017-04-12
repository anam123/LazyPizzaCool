package com.sahasu.lazypizza;

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
    TextView add;
    Button accept_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        if(getIntent() != null)
        {
            Intent in = getIntent();
            final String orderName = in.getStringExtra("orderName");
            final String placeby = in.getStringExtra("placeBy");
            final String address = in.getStringExtra("address");
            final String contact = in.getStringExtra("contact");
            final String cost = in.getStringExtra("cost");
            final String UID = in.getStringExtra("UID");

            order_name = (TextView) findViewById(R.id.textView2);
            place_by = (TextView) findViewById(R.id.textView3);
            contact_no = (TextView) findViewById(R.id.textView8);
            cost_value = (TextView) findViewById(R.id.textView4);
            add = (TextView) findViewById(R.id.textView7);
            Button button = (Button) findViewById(R.id.button);

            String f = "1 " + orderName;
            String s = "PLACED BY : " + placeby;
            String t = "ADDRESS : " + address;
            String fo = "CONTACT : " + contact;
            String fi = cost;

            order_name.setText(f);
            place_by.setText(s);
            contact_no.setText(fo);
            cost_value.setText(fi);
            add.setText(t);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), UID, Toast.LENGTH_SHORT).show();
                    Log.d("ANOTHER EASY TAG", UID);

                    data.orderCompleted(UID,placeby,data.email,cost.split(" ")[3]);
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
