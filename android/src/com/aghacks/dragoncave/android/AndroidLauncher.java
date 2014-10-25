package com.aghacks.dragoncave.android;

import android.os.Bundle;

import com.aghacks.dragoncave.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGLSurfaceView20API18 = false;
		String result = new String("To ja, androidLauncher");
		initialize(new Game(), config);
	}
}
