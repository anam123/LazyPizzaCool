package com.sahasu.lazypizza;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agam on 4/7/2017.
 */

public class MenuData {

    private static final String[] items= {"Bread Omelette","Samosa","Chk Sandwitch","Paneer Sandwitch","Veg Sandwitch",
            "Samosa-Chole"};

    public static List<MenuItems> getListData(){
        List<MenuItems> fmenu = new ArrayList<>();

        for (int i=0;i<items.length;i++)
        {
            MenuItems item = new MenuItems();
            item.setTitle(items[i]);
            item.setS_coins(5);
            item.setImageResId(R.mipmap.ic_launcher_round);
            item.setCost(20);
            fmenu.add(item);
        }

        return fmenu;
    }

}
