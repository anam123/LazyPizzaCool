package com.sahasu.lazypizza;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class menuFood extends Fragment {

    private RecyclerView recyclerView;
    private MenuAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.activity_menu_food,container,false);
        recyclerView= (RecyclerView) v.findViewById(R.id.list);
        adaptor = new MenuAdaptor(MenuData.getListData(),v.getContext());
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }

}
