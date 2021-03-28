package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOver implements Screen {

	private GameObj game;
	private SpriteBatch batch;
	private Texture img;
	private Texture myTexture, gameOver;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTexRegionDrawable;
	private ImageButton button, button2;
	private Stage stage;
	private Slider slider;
	public BitmapFont font;

	public GameOver(GameObj game) {
		this.game = game;
		font = new BitmapFont();
		batch = new SpriteBatch();

		// checkBox = new CheckBox("", skin);

	}
	
	public GameObj getGame() {
		return game;
	}
	
	public void getGameOver() {
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver(game));;
	}
	
	@Override
	public void show() { // first to get called
		//font = new BitmapFont();
//		batch = new SpriteBatch();

		myTexture = new Texture(Gdx.files.internal("back_button.png"));
		gameOver = new Texture(Gdx.files.internal("graveGameOver.png")); // gameOver image

		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button = new ImageButton(myTexRegionDrawable); // Set the button up
		button.setSize(200, 100);
		button.setPosition(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 600);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				// jumps back to mainmenuScreen can be set to any screen
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));


			}
		});
		stage = new Stage(new ScreenViewport()); // Set up a stage for the ui
		stage.addActor(button); // Add the button to the stage to perform rendering and take input.
		Gdx.input.setInputProcessor(stage); // Start taking input from the ui

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("Option Page");

		batch.begin();

		// font.draw(batch, "Music Volume", 250, 520);
		//batch.draw(gameOver, Gdx.graphics.getWidth() - 850, 150);
		batch.draw(gameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.setColor(Color.BLACK);

		batch.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();

	}

}