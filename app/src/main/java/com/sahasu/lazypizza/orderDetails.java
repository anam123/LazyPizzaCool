package com.sahasu.lazypizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class orderDetails extends AppCompatActivity {

    TextView orderDetailsItem;
    TextView orderDetailsPrice, orderDetailsLocation, orderDetailsTime, orderDetailsAccepted, orderDetailsDeliveryBoy, orderDetailsDeliveryBoyPhone,est_delivery;
    String item, price, destination, time, deliveryemail, deliveryphone, accepted ,sc,expdel,expected;
    String ID;
    LinearLayout ll;
    Button pay;
    Button cancel;
    Button call;
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
            expdel = intent.getStringExtra("Expectedtime");
            time = intent.getStringExtra("Time");
            accepted = intent.getStringExtra("Accepted");
            deliveryemail = intent.getStringExtra("DeliveryEmail");
            expected=intent.getStringExtra("exp");
            deliveryphone = intent.getStringExtra("DeliveryPhone");
            sc = intent.getStringExtra("SC");

            orderDetailsItem = (TextView)findViewById(R.id.orderDetailsTitle);
            orderDetailsPrice = (TextView) findViewById(R.id.orderDetailsPrice);
            orderDetailsLocation = (TextView) findViewById(R.id.orderDetailsLocation);
            orderDetailsTime = (TextView) findViewById(R.id.orderDetailsTime);
            orderDetailsAccepted = (TextView) findViewById(R.id.orderDetailsAccepted);
            orderDetailsDeliveryBoy = (TextView) findViewById(R.id.orderDetailsDeliveryBoy);
            orderDetailsDeliveryBoyPhone = (TextView) findViewById(R.id.orderDetailsDeliveryBoyPhone);
            est_delivery = (TextView) findViewById(R.id.est_delivery);

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
                String hour1 = expdel.split("_")[1].substring(0,2);
                String minutes1 = expdel.split("_")[1].substring(2,4);
                String ts1;
                if(Integer.parseInt(minutes1)+Integer.parseInt(expected)<60)
                {
                    minutes1=Integer.toString(Integer.parseInt(minutes1)+Integer.parseInt(expected));
                }
                else
                {
                    minutes1=Integer.toString(Integer.parseInt(minutes1)+Integer.parseInt(expected)- 60);
                    hour1=Integer.toString(Integer.parseInt(hour1)+1);

                }
                if(Integer.parseInt(hour) < 12)
                    ts1 = hour1 + ":" + minutes1 ;
                else
                    ts1 = hour1 + ":" + minutes1  ;


                est_delivery.setText(ts1);


            }
            else
            {
                orderDetailsAccepted.setText("No");
                ll.setVisibility(View.INVISIBLE);
            }


        }

        call=(Button)findViewById(R.id.CALL);


        if(accepted.equals("0")) {
            call.setEnabled(false);
            call.setTextColor(Color.DKGRAY);

        }
        else {
            call.setEnabled(true);
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + orderDetailsDeliveryBoyPhone.getText().toString()));
                startActivity(intent);


            }
        });

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

        cancel=(Button)findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SAVE THIS PHONE NUMBER IN THE DATABASE

                Log.d(data.email,"email");
                Log.d(deliveryemail,"email2");

                if(accepted.equals("0"))
                {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    data.orderCompleted(ID);
                                    Toast.makeText(orderDetails.this, "The order is removed from marketplace.", Toast.LENGTH_SHORT).show();
                                    Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getApplicationContext().startActivity(mainIntent);

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(orderDetails.this);
                    builder.setMessage("Are you sure you want to remove order from marketplace?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();



                }

                else{

                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    System.out.println("curr"+currentDateandTime);


                    try {
                        Date d=sdf.parse(expdel);
                        Calendar gc = new GregorianCalendar();
                        gc.setTime(d);
                        gc.add(Calendar.MINUTE, Integer.parseInt(expected));
                        Date d2 = gc.getTime();
                        String expdel1=sdf.format(d2);
                        System.out.println("ex"+expdel1);

                        if (sdf.parse(currentDateandTime).after(sdf.parse(expdel1)))
                        {

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            data.setValue("marketplace/"+ID+"/accepted","0");
                                            data.setValue("marketplace/"+ID+"/deliveryboy"," ");
                                            data.setValue("marketplace/"+ID+"/deliveryboyphone"," ");
                                            data.setValue("marketplace/"+ID+"/exp_timestamp"," ");
                                            Toast.makeText(orderDetails.this, "Order added back to MarketPlace", Toast.LENGTH_SHORT).show();
                                            Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(mainIntent);

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            data.orderCompleted(ID);
                                            Toast.makeText(orderDetails.this, "The order is removed from marketplace.", Toast.LENGTH_SHORT).show();
                                            Intent mainIntent1=new Intent(getApplicationContext(),MainActivity.class);
                                            mainIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(mainIntent1);

                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(orderDetails.this);
                            builder.setMessage("Are you sure?").setPositiveButton("Add Back To MarketPlace", dialogClickListener)
                                    .setNegativeButton("Delete Order", dialogClickListener).show();


                        }
                        else {

                            Toast.makeText(orderDetails.this, "You cannot cancel order before the expected delivery time.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (ParseException P)
                    {

                    }
                }

            }
        });




    }
}
