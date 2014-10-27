package com.aghacks.dragoncave.android.menu.achievements;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aghacks.dragoncave.android.R;

public class AchievementsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        SharedPreferences sharedPref = getPreferences(Context.MODE_APPEND);

        AchievementsBean RowBean_data[] = new AchievementsBean[] {

                new AchievementsBean(R.drawable.achieve_10, "Newbie", "Get the achievement by run 10 meters."),
                new AchievementsBean(R.drawable.achieve_100, "Amateur", "Fly for 100 meters, no less!"),
                new AchievementsBean(R.drawable.achieve_500, "Professional", "Do birds teach you how to fly? You are really good!"),
                new AchievementsBean(R.drawable.achieve_1000, "No-life", "You've become a skilled player in DragonCave!"),
        };

        for(int i=0; i<RowBean_data.length; i++) RowBean_data[i].setScored(sharedPref.getBoolean("Achievement_Scored_" + i, false));

        AchievementsAdapter adapter = new AchievementsAdapter(this,
                R.layout.single_achievement, RowBean_data);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);

    }
}
