package com.aghacks.dragoncave.android.menu.shop;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aghacks.dragoncave.android.R;
import com.aghacks.dragoncave.android.menu.MenuActivity;
import com.aghacks.dragoncave.android.menu.Useful;

public class ShopAdapter extends ArrayAdapter<ShopBean> {
    private Activity activity;
    int layoutResourceId;
    private ShopBean[] data;

    private int screenHeight;
    private int screenWidth;

    public ShopAdapter(Activity activity, int layoutResourceId, ShopBean[] data) {
        super(activity.getApplicationContext(), layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.activity = activity;
        this.data = data;

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.linearLayout = (LinearLayout) row.findViewById(R.id.single_shop_root);
            holder.txtTitle = (TextView) row.findViewById(R.id.shop_txt);
            holder.button = (Button) row.findViewById(R.id.shop_button);
            holder.imageItem = (ImageView) row.findViewById(R.id.shop_img);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        final SharedPreferences sharedPref = activity.getPreferences(Context.MODE_APPEND);
        final int money = sharedPref.getInt("Points", 0);


        final ShopBean item = data[position];
        data[position].setLayout(holder.linearLayout);
        holder.txtTitle.setText(item.getTitle());
        holder.imageItem.setImageBitmap(Useful.getScaledBitmap(activity.getResources(), item.getId(), 0, getWidgetH(0.25)));
        holder.button.setText(String.valueOf(item.getPrice()));
        for(ShopBean shopBean : data) {
            if(shopBean.getLayout() != null) {
                if (shopBean.isActivated()) shopBean.getLayout().setBackgroundColor(Color.argb(200, 255, 200, 150));
                else shopBean.getLayout().setBackgroundColor(Color.argb(150, 255, 200, 150));
                shopBean.getLayout().invalidate();
            }
        }

//        if(!item.isPurchased() && money < item.getPrice()) holder.button.setEnabled(false);
        final int pos = position;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(pos) {
                    case 0:
                        new ItemManager(sharedPref, pos) {
                            public void disable() {
                                editor.putBoolean("ShopItem_Activated_0", false);
                                editor.putInt("Background_Enabled", R.drawable.bg_cave);
                                MenuActivity.bg_cave.setImageBitmap(Useful.getScaledBitmap(
                                        activity.getResources(), R.drawable.bg_cave,
                                        screenWidth, screenHeight));
                                data[0].setActivated(false);
                            }
                            public void enable() {
                                editor.putBoolean("ShopItem_Activated_0", true);
                                editor.putInt("Background_Enabled", R.drawable.image_shop_1);
                                MenuActivity.bg_cave.setImageBitmap(Useful.getScaledBitmap(
                                        activity.getResources(), R.drawable.image_shop_1,
                                        screenWidth, screenHeight));
                                data[0].setActivated(true);
                            }
                            public void buy() {
                                editor.putBoolean("ShopItem_Purchase_0", true);
                                editor.putBoolean("ShopItem_Activated_0", false);
                                editor.putInt("Points", money - item.getPrice());
                                data[0].setPurchased(true);
                                data[0].setActivated(false);
                            }
                        }.perform();
                        break;
                    case 1:
                        new ItemManager(sharedPref, pos) {
                            public void disable() {
                                editor.putBoolean("ShopItem_Activated_1", false);
                                editor.putInt("Dragon_Enabled", R.drawable.image_dragon);
                                MenuActivity.image_dragon.setImageBitmap(Useful.getScaledBitmap(
                                        activity.getResources(), R.drawable.image_dragon,
                                        0, getWidgetH(0.452)));
                                data[1].setActivated(false);
                            }
                            public void enable() {
                                editor.putBoolean("ShopItem_Activated_1", true);
                                editor.putBoolean("ShopItem_Activated_2", false);
                                editor.putInt("Dragon_Enabled", R.drawable.image_shop_2);
                                MenuActivity.image_dragon.setImageBitmap(Useful.getScaledBitmap(
                                        activity.getResources(), R.drawable.image_shop_2,
                                        0, getWidgetH(0.452)));
                                data[1].setActivated(true);
                                data[2].setActivated(false);
                            }
                            public void buy() {
                                editor.putBoolean("ShopItem_Purchase_1", true);
                                editor.putBoolean("ShopItem_Activated_1", false);
                                editor.putInt("Points", money - item.getPrice());
                                data[1].setPurchased(true);
                                data[1].setActivated(false);
                            }
                        }.perform();
                        break;
                    case 2:
                        new ItemManager(sharedPref, pos) {
                            public void disable() {
                                editor.putBoolean("ShopItem_Activated_2", false);
                                editor.putInt("Dragon_Enabled", R.drawable.image_dragon);
                                MenuActivity.image_dragon.setImageBitmap(Useful.getScaledBitmap(
                                        activity.getResources(), R.drawable.image_dragon,
                                        0, getWidgetH(0.452)));
                                data[2].setActivated(false);
                            }
                            public void enable() {
                                editor.putBoolean("ShopItem_Activated_2", true);
                                editor.putBoolean("ShopItem_Activated_1", false);
                                editor.putInt("Dragon_Enabled", R.drawable.image_shop_3);
                                MenuActivity.image_dragon.setImageBitmap(Useful.getScaledBitmap(
                                        activity.getResources(), R.drawable.image_shop_3,
                                        0, getWidgetH(0.452)));
                                data[2].setActivated(true);
                                data[1].setActivated(false);
                            }
                            public void buy() {
                                editor.putBoolean("ShopItem_Purchase_2", true);
                                editor.putBoolean("ShopItem_Activated_2", false);
                                editor.putInt("Points", money - item.getPrice());
                                data[2].setPurchased(true);
                                data[2].setActivated(false);
                            }
                        }.perform();
                        break;
                }
            }
        });
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        Button button;
        ImageView imageItem;
        LinearLayout linearLayout;
    }
    private int getWidgetW(double percent) {
        return (int)(percent*screenWidth);
    }
    private int getWidgetH(double percent) {
        return (int)(percent*screenHeight);
    }

    abstract class ItemManager {

        SharedPreferences sp;
        SharedPreferences.Editor editor;
        int pos;

        public ItemManager(SharedPreferences sp1, int position) {
            this.sp = sp1;
            this.pos = position;
        }

        public abstract void disable();
        public abstract void enable();
        public abstract void buy();

        public void perform() {
            this.editor = sp.edit();
            if(sp.getBoolean("ShopItem_Purchase_"+pos, false)) {
                if(sp.getBoolean("ShopItem_Activated_"+pos, false)) {
                    disable();
                } else {
                    enable();
                }
            } else {
                buy();
            }
            editor.apply();
            for(ShopBean shopBean : data) {
                if(shopBean.getLayout() != null) {
                    if (shopBean.isActivated()) shopBean.getLayout().setBackgroundColor(Color.argb(200, 255, 200, 150));
                    else shopBean.getLayout().setBackgroundColor(Color.argb(150, 255, 200, 150));
                    shopBean.getLayout().invalidate();
                }
            }
            activity.findViewById(R.id.shop_root).invalidate();
        }

    }
}