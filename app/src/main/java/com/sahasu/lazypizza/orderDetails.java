package com.sahasu.lazypizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class orderDetails extends AppCompatActivity {

    TextView orderDetailsItem;
    TextView orderDetailsPrice, orderDetailsLocation, orderDetailsTime, orderDetailsAccepted, orderDetailsDeliveryBoy, orderDetailsDeliveryBoyPhone;
    String item, price, destination, time, deliveryemail, deliveryphone, accepted ,sc;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        if(getIntent() != null)
        {
            Intent intent = getIntent();
            item = intent.getStringExtra("Item");
            price = intent.getStringExtra("Price");
            destination = intent.getStringExtra("Destination");
            time = intent.getStringExtra("Time");
            accepted = intent.getStringExtra("Accepted");
            deliveryemail = intent.getStringExtra("Deliveryemail");
            deliveryphone = intent.getStringExtra("Deliveryphone");
            sc = intent.getStringExtra("SC");

            orderDetailsItem = (TextView)findViewById(R.id.orderDetailsTitle);
            orderDetailsPrice = (TextView) findViewById(R.id.orderDetailsPrice);
            orderDetailsLocation = (TextView) findViewById(R.id.orderDetailsLocation);
            orderDetailsTime = (TextView) findViewById(R.id.orderDetailsTime);
            orderDetailsAccepted = (TextView) findViewById(R.id.orderDetailsAccepted);
            orderDetailsDeliveryBoy = (TextView) findViewById(R.id.orderDetailsDeliveryBoy);
            orderDetailsDeliveryBoyPhone = (TextView) findViewById(R.id.orderDetailsDeliveryBoyPhone);

            ll = (LinearLayout) findViewById(R.id.orderDetailsDeliveryBoyLayout);

            orderDetailsItem.setText(item);
            orderDetailsPrice.setText("Rs. " + price + " + SC " + sc);
            orderDetailsLocation.setText(destination);
            orderDetailsTime.setText(time);
            if(accepted.equals("1"))
            {
                orderDetailsAccepted.setText("Yes");
                ll.setVisibility(View.VISIBLE);
                orderDetailsDeliveryBoy.setText(deliveryemail);
                orderDetailsDeliveryBoyPhone.setText(deliveryphone);
            }
            else
            {
                orderDetailsAccepted.setText("No");
                ll.setVisibility(View.INVISIBLE);
            }


        }






    }
}
