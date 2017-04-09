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
            int j;
            int[] icons = {R.drawable.pizza, R.drawable.pizza, R.drawable.cheezydibble, R.drawable.cheezydibble,R.drawable.food,R.drawable.cake,R.drawable.cheetos,R.drawable.cake,R.drawable.cheezydibble};
            for (int i = 0; i < com.sahasu.lazypizza.data.items.size(); i++) {
                MenuInfo current = new MenuInfo();
                j=i%icons.length;
                current.setImage_id(icons[j]);
                current.setOrder_name(com.sahasu.lazypizza.data.items.get(i).get("name"));
                current.setCost("Rs " + com.sahasu.lazypizza.data.items.get(i).get("price"));
                data.add(current);
            }
        }
        return data;
    }

}
