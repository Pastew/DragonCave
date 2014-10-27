package com.aghacks.dragoncave.android.menu;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.aghacks.dragoncave.android.R;

/**
 * Created by Lukasz on 25/10/2014.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
