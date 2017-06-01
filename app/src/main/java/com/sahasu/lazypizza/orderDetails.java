package com.sahasu.lazypizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class orderDetails extends AppCompatActivity {

    TextView orderDetailsItem;
    TextView orderDetailsPrice, orderDetailsLocation, orderDetailsTime, orderDetailsAccepted, orderDetailsDeliveryBoy, orderDetailsDeliveryBoyPhone;
    String item, price, destination, time, deliveryemail, deliveryphone, accepted ,sc;
    String ID;
    LinearLayout ll;
    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        if(getIntent() != null)
        {
            Intent intent = getIntent();
            item = intent.getStringExtra("Item");
            price = intent.getStringExtra("Price");
            ID=intent.getStringExtra("UID");
            destination = intent.getStringExtra("Destination");
            time = intent.getStringExtra("Time");
            accepted = intent.getStringExtra("Accepted");
            deliveryemail = intent.getStringExtra("DeliveryEmail");
            deliveryphone = intent.getStringExtra("DeliveryPhone");
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

            String hour = time.split("_")[1].substring(0,2);
            String minutes = time.split("_")[1].substring(2,4);
            String ts;
            if(Integer.parseInt(hour) < 12)
                ts = hour + ":" + minutes + " am";
            else
                ts = hour + ":" + minutes + " pm";
            orderDetailsTime.setText(ts);
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


        pay=(Button)findViewById(R.id.button3);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SAVE THIS PHONE NUMBER IN THE DATABASE

                Log.d(data.email,"email");
                Log.d(deliveryemail,"email2");

                if(accepted.equals("0"))
                {
                    Toast.makeText(orderDetails.this, "Your order hasn't been picked up yet.", Toast.LENGTH_SHORT).show();

                }

                else{
                    String e1=data.stringToEmail(deliveryemail);
                    data.transferSC(data.email,e1,Float.parseFloat(sc),0);
                    data.orderCompleted(ID);
                    //display toast
                    Toast.makeText(orderDetails.this, "SCs have been transferred.", Toast.LENGTH_SHORT).show();
                    Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(mainIntent);
                    // start mainActivity
                }

            }
        });





    }
}
