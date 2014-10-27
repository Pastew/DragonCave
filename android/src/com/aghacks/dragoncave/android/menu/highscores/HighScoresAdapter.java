package com.aghacks.dragoncave.android.menu.highscores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aghacks.dragoncave.android.R;

public class HighScoresAdapter extends ArrayAdapter<HighScoresBean> {

    Context context;
    int layoutResourceId;
    HighScoresBean data[] = null;

    public HighScoresAdapter(Context context, int layoutResourceId, HighScoresBean[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;
 
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
 
            holder = new RowBeanHolder();
            holder.score = (TextView)row.findViewById(R.id.high_score);
            holder.name = (TextView)row.findViewById(R.id.high_name);
 
            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }
 
        HighScoresBean object = data[position];
        holder.name.setText(object.getName());
        holder.score.setText(String.valueOf(object.getScore()));

        return row;
    }
 
    static class RowBeanHolder
    {
        TextView name;
        TextView score;
    }
}