package com.aghacks.dragoncave.android.menu.highscores;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aghacks.dragoncave.android.R;

public class HighScoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        HighScoresBean RowBean_data[] = new HighScoresBean[] {

                new HighScoresBean("Mercedes1", 1234),
                new HighScoresBean("Mercedes1", 12389),
                new HighScoresBean("Mercedes1", 123456),
                new HighScoresBean("Mercedes1", 123489),
                new HighScoresBean("Mercedes1", 1234890),
        };

        HighScoresAdapter adapter = new HighScoresAdapter(this,
                R.layout.single_highscore, RowBean_data);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);

    }
}
