package com.sahasu.lazypizza;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MenuMain extends Fragment {

    private RecyclerView recyclerView;
    private MenuAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.activity_menu_food,container,false);
        recyclerView= (RecyclerView) v.findViewById(R.id.list);
        adaptor = new MenuAdaptor(getData(),v.getContext());
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }

    public static List<MenuInfo> getData()
    {
        List<MenuInfo> data = new ArrayList<>();

        if (com.sahasu.lazypizza.data.items!=null) {
            int j=0;
            int[] icons = {R.drawable.burger, R.drawable.sandwich, R.drawable.cheetos, R.drawable.cake, R.drawable.pizza};
            for (int i = 0; i < com.sahasu.lazypizza.data.items.size(); i++) {
                MenuInfo current = new MenuInfo();

                if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Burger"))
                {
                    j=0;
                }
                else if (com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chips"))
                {
                    j=2;
                }
                else if( com.sahasu.lazypizza.data.items.get(i).get("name").equals("Chicken Sandwich"))
                {
                    j=1;
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Paneer Sandwich"))
                {
                    j=1;
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Pastry"))
                {
                    j=3;
                }
                else if(com.sahasu.lazypizza.data.items.get(i).get("name").equals("Pizza"))
                {
                    j=4;
                }

                current.setImage_id(icons[j]);
                current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                current.setCost(com.sahasu.lazypizza.data.items.get(i).get("price"));
                current.setSource(com.sahasu.lazypizza.data.items.get(i).get("source"));
                data.add(current);
            }
        }
        return data;
    }

}
