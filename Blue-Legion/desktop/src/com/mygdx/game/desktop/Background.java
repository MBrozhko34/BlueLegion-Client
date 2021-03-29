package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Background {
	private Texture backdrop;

	public Background() {
	}

	public Texture getBackground(String name) {
		backdrop = new Texture(Gdx.files.internal(name));
		return backdrop;
	}
}
