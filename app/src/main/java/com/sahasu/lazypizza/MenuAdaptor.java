package com.sahasu.lazypizza;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.MyHolder>{

    private List<MenuItems> menuData;
    private LayoutInflater inflater;

    public MenuAdaptor(List<MenuItems> menuData, Context c)
    {
        this.inflater = LayoutInflater.from(c);
        this.menuData=menuData;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menuitem,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MenuItems menu= menuData.get(position);
        holder.title.setText(menu.getTitle());
//        holder.icon.setImageResource(menu.getImageResId());
    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView icon;
        private View container;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.item_title);
//            icon = (Linea)itemView.findViewById(R.id.item_icon);
//            container = itemView.findViewById(R.id.)
        }
    }
}
