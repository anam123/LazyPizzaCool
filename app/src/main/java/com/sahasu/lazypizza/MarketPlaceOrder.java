package com.sahasu.lazypizza;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
            final String exp = in.getStringExtra("expected");
            final String contact = in.getStringExtra("contact");
            final String src = in.getStringExtra("source");
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

            String f = orderName;
            String s = "Placed by : " + placeby;
            String t = "Address : " + address;
            String fo = "Contact : " + contact;
            String fi = cost;
            String si = "Time : " + ts;

            Toast.makeText(getApplicationContext(), "Source " + src, Toast.LENGTH_SHORT).show();
            order_name.setText(f);
            place_by.setText(s);
            contact_no.setText(fo);
            cost_value.setText(fi);
            add.setText(t);
            timestamp.setText(si);


            accept_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(data.email,"aa");
                    Log.d(placeby,"ll");

                    if(data.email.equals(placeby)){


                        System.out.print("ss");
                        Toast.makeText(v.getContext(), "Cannot deliver order placed by you.", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent1);

                    }
                    else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String currentDateandTime = sdf.format(new Date());

                        data.setValue("marketplace/" + UID + "/exp_timestamp", currentDateandTime);
                        data.setValue("marketplace/" + UID + "/expected", exp);
                        Toast.makeText(v.getContext(), UID, Toast.LENGTH_SHORT).show();
                        Log.d("ANOTHER EASY TAG", "/" + cost + "/");
                        int count = 0;


                        data.acceptItemFromMarket(UID);

                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(v.getContext());

//Create the intent that’ll fire when the user taps the notification//

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(v.getContext(), 0, intent, 0);

                        mBuilder.setContentIntent(pendingIntent);

                        mBuilder.setSmallIcon(R.drawable.logo);
                        mBuilder.setContentTitle("You have accepted an order.");
                        mBuilder.setContentText("Expected Delivery Time: " + exp + " " + "mins");

                        NotificationManager mNotificationManager =

                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        mNotificationManager.notify(001, mBuilder.build());

                        //data.orderCompleted(UID,placeby,data.email,cost.split(" ")[3]);
                        Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent1);
                    }
                }
            });
        }
    }



    public void onBackPressed()
    {
        finish();
    }
}
