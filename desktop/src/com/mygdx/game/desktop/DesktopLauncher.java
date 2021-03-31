package com.mygdx.game.desktop;

import java.awt.Toolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// sets the size of the game window to be based off the size of the screen being
		// used
		System.out.println("Started");
		config.height = 800;
		config.width = 1000;
		config.resizable=false;
		// locks the frame rate
		config.foregroundFPS = 30;
		new LwjglApplication(new GameObj(), config); //GameObj is the controller for the screens it decides what runs first 
	}
}
