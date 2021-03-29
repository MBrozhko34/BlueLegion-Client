package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character implements ApplicationListener {

	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 4, FRAME_ROWS = 5;

	// Objects used
	Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
	Texture walkSheet;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	
	 int x = 200;
	 int y = 200;
	 int w = 200;
	 int h = 200;
	 float frames =10.0f;

	// A variable for tracking elapsed time for the animation
	float stateTime;
	
	private String name;

	public Character(String name) {
		
	 this.name = name;

		// Load the sprite sheet as a Texture
		walkSheet = new Texture(Gdx.files.internal(name+".png"));
		
		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		} 
		
		walkAnimation = new Animation<TextureRegion>(frames, walkFrames);

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		stateTime = 0f;

	}
	public void position(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		spriteBatch.begin(); 
		spriteBatch.draw(currentFrame, x, y, w, h);
		spriteBatch.end();
	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		walkSheet.dispose();
	}
 

	@Override
	public void resize(int w, int h) {
		this.w = w;
		this.h = h;

	}
	
	public Character getObject() {
		return this;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	public Character getAnimation() {
		return this;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}
}