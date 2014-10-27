package com.aghacks.dragoncave.android.menu.shop;

import android.widget.LinearLayout;

/**
 * Created by Lukasz on 25/10/2014.
 */
public class ShopBean {

    private String title;
    private int id;
    private int price;
    private boolean purchased;
    private boolean activated;
    private LinearLayout layout;

    public ShopBean(String title, int id, int price) {
        this.title = title;
        this.id = id;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }
}
