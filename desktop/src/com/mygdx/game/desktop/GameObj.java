package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//this class will be called only which says which screen will be first displayed 

public class GameObj extends Game {// games to handle game menues
	Music music;

	@Override
	public void create() {
		 this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {

		super.render(); // needed for setScreen
	}

	@Override
	public void dispose() {

	}

}
