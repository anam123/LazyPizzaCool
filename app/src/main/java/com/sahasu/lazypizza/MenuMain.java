package com.sahasu.lazypizza;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuMain extends Fragment {

    private RecyclerView recyclerView;
    private MenuAdaptor adaptor;
    TabHost tabHost;
    EditText filt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v= inflater.inflate(R.layout.activity_menu_food,container,false);
        filt=(EditText)v.findViewById(R.id.filter);
        final TabHost host = (TabHost)v.findViewById(R.id.tabHost);

        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Canteen");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Canteen");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("CDX");
        spec.setContent(R.id.tab2);
        spec.setIndicator("CDX");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Juice Shop");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Juice Shop");
        host.addTab(spec);

        host.setCurrentTab(0);
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
            adaptor = new MenuAdaptor(getData("Canteen"), v.getContext());
            recyclerView.setAdapter(adaptor);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#FF7C4DFF")); // selected
            TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
            tv.setTextColor(Color.parseColor("#ffffff"));



        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed

               if(tabId.equals(tabId)){


                   recyclerView= (RecyclerView) v.findViewById(R.id.list);
                   adaptor = new MenuAdaptor(getData(tabId),v.getContext());
                   recyclerView.setAdapter(adaptor);


                   recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));





               }

                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
                    host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff")); // unselected
                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#000000"));
                }

                host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#FF7C4DFF")); // selected
                TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));


            }
        });

        filt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter

                adaptor.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;


    }


    public static List<MenuInfo> getData(String tab)
    {
        Log.d(tab,"ssd");
        List<MenuInfo> data = new ArrayList<>();

        if (com.sahasu.lazypizza.data.items!=null) {
            int j=0;
            int[] icons = {R.drawable.burger, R.drawable.sandwich, R.drawable.cheetos, R.drawable.cake, R.drawable.pizza, R.drawable.shakes,R.drawable.juices};
            for (int i = 0; i < com.sahasu.lazypizza.data.items.size(); i++) {
                MenuInfo current = new MenuInfo("","","","",0);

                if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Veg Burger") & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=0;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chicken Burger") & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=0;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if (com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chips")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=2;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if( com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chicken Sandwich")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=1;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Paneer Sandwich")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=1;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Pastry")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=3;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Pizza")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=4;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Banana Shake")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=5;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Mango Shake")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=5;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Shikanji (Small)")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=6;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Orange Drink")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=6;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chocolate Shake")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=5;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chole Kulche")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=4;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Veg Puff")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=4;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Cappuccino")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=4;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Cheese Tomato Sandwich")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=1;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Banana Chips")  & com.sahasu.lazypizza.data.items.get(i).get("source").equals(tab))
                {
                    j=2;
                    current.setImage_id(icons[j]);
                    current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                    current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                    current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                    data.add(current);
                }






            }
        }
        return data;
    }

}
