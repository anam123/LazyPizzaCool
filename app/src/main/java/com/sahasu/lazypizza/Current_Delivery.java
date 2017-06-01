package com.sahasu.lazypizza;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by AnamBhatia on 01/06/17.
 */

public class Current_Delivery extends AppCompatActivity{

    TextView contact;
    TextView time;
    TextView location;
    TextView orderdetails;
    TextView price;
    TextView havent;
    TextView dd;
    LinearLayout ll;
    LinearLayout ll1;
    View v1;
    Button call;
    Button cancel;
    String UID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_delivery);
        contact=(TextView)findViewById(R.id.phonedel);
        time=(TextView)findViewById(R.id.timeofdel);
        location=(TextView)findViewById(R.id.locdel);
        orderdetails=(TextView)findViewById(R.id.orderDetailsTitle);
        price=(TextView)findViewById(R.id.orderDetailsPrice);
        dd=(TextView)findViewById(R.id.detailstext);
        havent=(TextView)findViewById(R.id.havent);
        ll=(LinearLayout)findViewById(R.id.linearLayout4);
        ll1=(LinearLayout)findViewById(R.id.linearLayout5);
        v1=(View)findViewById(R.id.myRectangleView);
        call=(Button)findViewById(R.id.call);
        cancel=(Button)findViewById(R.id.cancel);



        int count=0;
        for (int i = 0; i < com.sahasu.lazypizza.data.market.size(); i++) {

            if (com.sahasu.lazypizza.data.market.get(i).get("accepted").equals("1")) {
                if (data.stringToEmail(com.sahasu.lazypizza.data.market.get(i).get("deliveryboy")).equals(data.email)){

                    String hour = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(0,2);
                    String minutes = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(2,4);
                    String ts;
                    String exp=com.sahasu.lazypizza.data.market.get(i).get("expected");
                     UID=com.sahasu.lazypizza.data.market.get(i).get("UID");
                    if(Integer.parseInt(minutes)+Integer.parseInt(exp)<60)
                    {
                        minutes=Integer.toString(Integer.parseInt(minutes)+Integer.parseInt(exp));
                    }
                    else
                    {
                        minutes=Integer.toString(Integer.parseInt(minutes)+Integer.parseInt(exp)- 60);
                        hour=Integer.toString(Integer.parseInt(hour)+1);

                    }
                    if(Integer.parseInt(hour) < 12)
                        ts = hour + ":" + minutes + " am";
                    else
                        ts = hour + ":" + minutes + " pm";

                    count=1;
                    contact.setText(com.sahasu.lazypizza.data.market.get(i).get("phone"));
                    time.setText(ts);
                    location.setText(com.sahasu.lazypizza.data.market.get(i).get("destination"));
                    orderdetails.setText(com.sahasu.lazypizza.data.market.get(i).get("item"));
                    price.setText("Rs. "+com.sahasu.lazypizza.data.market.get(i).get("price")+" + "+"SC "+com.sahasu.lazypizza.data.market.get(i).get("SC") );
                    break;
                }
            }
        }

        if(count==0)
        {
            havent.setVisibility(View.VISIBLE);
            ll.setVisibility(View.INVISIBLE);
            ll1.setVisibility(View.INVISIBLE);
            v1.setVisibility(View.VISIBLE);
            dd.setVisibility(View.INVISIBLE);
            orderdetails.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
            call.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);

            v1.setVisibility(View.VISIBLE);

        }
        else
        {
            ll.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
            dd.setVisibility(View.VISIBLE);
            orderdetails.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            call.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            havent.setVisibility(View.INVISIBLE);

        }


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contact.getText().toString()));
                startActivity(intent);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                data.setValue("marketplace/"+UID+"/accepted","0");
                data.setValue("marketplace/"+UID+"/deliveryboy"," ");
                data.setValue("marketplace/"+UID+"/deliveryboyphone"," ");
                Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(mainIntent);

            }
        });


    }

}

