package com.aghacks.dragoncave.android.menu.achievements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aghacks.dragoncave.android.R;
import com.aghacks.dragoncave.android.menu.Useful;

public class AchievementsAdapter extends ArrayAdapter<AchievementsBean> {
 
    Context context;
    int layoutResourceId;   
    AchievementsBean data[] = null;

    int screenHeight;
    int screenWidth;
 
    public AchievementsAdapter(Context context, int layoutResourceId, AchievementsBean[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;
 
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RowBeanHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtDesc = (TextView)row.findViewById(R.id.txtDesc);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.layout = (LinearLayout)row.findViewById(R.id.achievement_layout);

            row.setTag(holder);
        } else {
            holder = (RowBeanHolder)row.getTag();
        }
 
        AchievementsBean object = data[position];
        holder.txtTitle.setText(object.getTitle());
        holder.txtDesc.setText(object.getDescription());
        Bitmap bitmap = Useful.getScaledBitmap(context.getResources(), object.getId(), 0, getWidgetH(0.40));
        holder.imgIcon.setImageBitmap(bitmap);
        if(object.isScored()) {
            holder.txtTitle.setTextColor(Color.WHITE);
            holder.layout.setBackgroundColor(Color.argb(100, 100, 100, 100));
        } else {
            holder.txtTitle.setTextColor(Color.RED);
            holder.layout.setBackgroundColor(Color.argb(100, 255, 140, 180));
        }
        return row;
    }
 
    static class RowBeanHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDesc;
        LinearLayout layout;
    }

    private int getWidgetW(double percent) {
        return (int)(percent*screenWidth);
    }
    private int getWidgetH(double percent) {
        return (int)(percent*screenHeight);
    }}