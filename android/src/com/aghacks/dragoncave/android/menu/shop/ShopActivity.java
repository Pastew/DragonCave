package com.aghacks.dragoncave.android.menu.shop;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aghacks.dragoncave.android.R;
import com.aghacks.dragoncave.android.menu.Useful;


public class ShopActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_shop);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ShopBean RowBean_data[] = new ShopBean[] {
                new ShopBean("Winter theme", R.drawable.image_shop_1, 0),
                new ShopBean("Skilled red dragon", R.drawable.image_shop_2, 0),
                new ShopBean("Diablo with liver", R.drawable.image_shop_3, 0),
        };

        SharedPreferences sharedPref = getPreferences(Context.MODE_APPEND);

        for(int i=0; i<RowBean_data.length; i++) {
            RowBean_data[i].setPurchased(sharedPref.getBoolean("ShopItem_Purchase_" + i, false));
            RowBean_data[i].setActivated(sharedPref.getBoolean("ShopItem_Activated_" + i, false));
        }

        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_shop);
        }

        final TextView myTitleText = (TextView) findViewById(R.id.shop_counter);
        myTitleText.setText(String.valueOf(sharedPref.getInt("Points", 0)));
        myTitleText.setTextColor(Color.BLACK);

        ImageView eggs = (ImageView) findViewById(R.id.shop_eggs);
        eggs.setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.image_dragon_egg, 0, 100));

        GridView gridView = (GridView) findViewById(R.id.gridView);
        ShopAdapter sa = new ShopAdapter(this, R.layout.single_shop_item, RowBean_data);
        gridView.setAdapter(sa);

    }

}
