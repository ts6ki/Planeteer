package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// !!!NOTE: that on macOS the application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	/**
	 * @param arg
	 */
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Planeteer");
		config.setWindowedMode(720, 1080);
		config.useVsync(true);
		config.setForegroundFPS(144);
		new Lwjgl3Application(new Game(), config);
	}
}
