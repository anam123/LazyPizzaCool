package com.sahasu.lazypizza;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MarketMain extends Fragment {

    private Boolean exit = false;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MarketAdaptor adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.market_act_main,container,false);
        recyclerView= (RecyclerView) v.findViewById(R.id.orderList);

        adapter = new MarketAdaptor(v.getContext(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }



    public static List<MarketInfo> getData()
    {
        List<MarketInfo> data = new ArrayList<>();

//        int[] icons = {R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4};
//        String[] order_bys = {"sarthak14094@iiitd.ac.in" , "utsav14113@iiitd.ac.in" , "himanshu14045@iiitd.ac.in", "agam14008@iiitd.ac.in"};
//        String[] orderNames = {"Pizza" , "Samosa", "French Fries", "Burger"};
//        String[] addresses= {"C-217", "A-515", "B-220", "C-301"};
//        String[] costs = {"Rs 50 + 5 SC" , "Rs 50 + 10 SC", "Rs 50 + 8 SC", "Rs 50 + 100 SC"};
//        String[] phone_no = {"9019230123", "1231241244", "1275891514", "1294809123"};
//        String[] UID = {"0","1","2","3"};
//
//        for(int i=0;i<order_bys.length;i++)
//        {
//            MarketInfo current = new MarketInfo();
////            current.img_id = icons[i];
//            current.order_name = orderNames[i];
//            current.address = addresses[i];
//            current.cost = costs[i];
//            current.place_by = order_bys[i];
//            current.phone_no = phone_no[i];
//            data.add(current);
//        }
        if (com.sahasu.lazypizza.data.market!=null) {
            int[] icons = {R.drawable.burger, R.drawable.sandwich, R.drawable.cheetos, R.drawable.cake,R.drawable.pizza};
            for (int i = 0; i < com.sahasu.lazypizza.data.market.size(); i++) {
                MarketInfo current = new MarketInfo();
                current.img_id=icons[0];
                if(com.sahasu.lazypizza.data.market.get(i).get("item").equals("Burger "))
                {
                    current.img_id=icons[0];
                }
                else if (com.sahasu.lazypizza.data.market.get(i).get("item").equals("Chips "))
                {
                    current.img_id=icons[2];
                }
                else if( com.sahasu.lazypizza.data.market.get(i).get("item").equals("Chicken Sandwich "))
                {
                    current.img_id=icons[1];
                }
                else if(com.sahasu.lazypizza.data.market.get(i).get("item").equals("Paneer Sandwich "))
                {
                    current.img_id=icons[1];
                }
                else if(com.sahasu.lazypizza.data.market.get(i).get("item").equals("Pastry "))
                {
                    current.img_id=icons[3];
                }
                else if(com.sahasu.lazypizza.data.market.get(i).get("item").equals("Pizza "))
                {
                    current.img_id=icons[4];
                }


                current.order_name = com.sahasu.lazypizza.data.market.get(i).get("item");
                current.address = com.sahasu.lazypizza.data.market.get(i).get("destination");
                current.cost = "Rs " + com.sahasu.lazypizza.data.market.get(i).get("price") + " + " + com.sahasu.lazypizza.data.market.get(i).get("SC") + " SC";
                current.place_by = com.sahasu.lazypizza.data.market.get(i).get("email");
                current.phone_no = com.sahasu.lazypizza.data.market.get(i).get("phone");
                current.UID =  com.sahasu.lazypizza.data.market.get(i).get("UID");
                current.time_stamp = com.sahasu.lazypizza.data.market.get(i).get("timestamp");
                current.src = com.sahasu.lazypizza.data.market.get(i).get("src");
                Log.d(current.src,"ff");
              //  Log.d("VERY EASY TO FIND TAG", com.sahasu.lazypizza.data.market.get(i).get("UID"));
                if(com.sahasu.lazypizza.data.market.get(i).get("accepted").equals("0"))
                    data.add(current);
            }
        }

        Collections.sort(data, new Comparator<MarketInfo>() {
            DateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");
            @Override
            public int compare(MarketInfo o1, MarketInfo o2) {
                try {
                    return f.parse(o1.time_stamp).compareTo(f.parse(o2.time_stamp));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        Collections.reverse(data);

        return data;
    }



//    @Override
//    public void onBackPressed() {
//        if (exit) {
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back
// again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
//
//        }
//
//    }
}
