package com.sahasu.lazypizza;

/**
 * Created by Agam on 4/7/2017.
 */

public class MenuItems {
    private String title;
    private int cost;
    private int s_coins;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setS_coins(int s_coins) {
        this.s_coins = s_coins;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    private int imageResId;

    public String getTitle() {
        return title;
    }

    public int getCost() {
        return cost;
    }

    public int getS_coins() {
        return s_coins;
    }

    public int getImageResId() {
        return imageResId;
    }
}
