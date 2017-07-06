package com.sahasu.lazypizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Calendar;

/**
 * Created by AnamBhatia on 01/06/17.
 */

public class Current_Delivery extends Fragment{

    TextView contact;
    TextView time;
    TextView location;
    TextView orderdetails;
    TextView price;
    TextView havent;
    TextView dd;
    LinearLayout ll;
    LinearLayout ll1;

    Button call;
    Button cancel;
    String UID="";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Current Delivery");

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.your_delivery, container, false);


        contact=(TextView)v.findViewById(R.id.phonedel);
        time=(TextView)v.findViewById(R.id.timeofdel);
        location=(TextView)v.findViewById(R.id.locdel);
        orderdetails=(TextView)v.findViewById(R.id.orderDetailsTitle);
        price=(TextView)v.findViewById(R.id.orderDetailsPrice);
        dd=(TextView)v.findViewById(R.id.detailstext);
        havent=(TextView)v.findViewById(R.id.havent);
        ll=(LinearLayout)v.findViewById(R.id.linearLayout4);
        ll1=(LinearLayout)v.findViewById(R.id.linearLayout5);

        call=(Button)v.findViewById(R.id.call);
        cancel=(Button)v.findViewById(R.id.cancel);

        final Typeface type=Typeface.createFromAsset(v.getContext().getAssets(),"Arcon-Regular.otf");
        contact.setTypeface(type);
        time.setTypeface(type);
        location.setTypeface(type);
        orderdetails.setTypeface(type);
        price.setTypeface(type);
        dd.setTypeface(type);
        havent.setTypeface(type);
        call.setTypeface(type);
        cancel.setTypeface(type);








        int count=0;
        for (int i = 0; i < com.sahasu.lazypizza.data.market.size(); i++) {

            if (com.sahasu.lazypizza.data.market.get(i).get("accepted").equals("1")) {
                if (data.stringToEmail(com.sahasu.lazypizza.data.market.get(i).get("deliveryboy")).equals(data.email)){

                    System.out.println("ee"+com.sahasu.lazypizza.data.market.get(i).get("exp_timestamp"));



                    String hour=com.sahasu.lazypizza.data.market.get(i).get("exp_timestamp").split("_")[1].substring(0,2);
                    String minutes=com.sahasu.lazypizza.data.market.get(i).get("exp_timestamp").split("_")[1].substring(2,4);
                    System.out.println(hour);
                    System.out.println(minutes);
                    //String hour = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(0,2);
                    // String minutes = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(2,4);
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

            dd.setVisibility(View.INVISIBLE);
            orderdetails.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
            call.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);



        }
        else
        {
            ll.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.VISIBLE);

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
            public void onClick(final View v) {



                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                data.setValue("marketplace/"+UID+"/accepted","0");
                                data.setValue("marketplace/"+UID+"/deliveryboy"," ");
                                data.setValue("marketplace/"+UID+"/deliveryboyphone"," ");
                                data.setValue("marketplace/"+UID+"/exp_timestamp"," ");
                                Intent mainIntent=new Intent(v.getContext(),MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                v.getContext().startActivity(mainIntent);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to cancel this delivery?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });
        return v;

    }


}

