package com.aghacks.dragoncave.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.aghacks.dragoncave.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.height = 720;
		cfg.width = 1280;
		new LwjglApplication(new Game(), cfg);
	}
}
