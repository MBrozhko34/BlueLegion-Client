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

public class OptionScreen implements Screen {

	private GameObj game;
	private SpriteBatch batch;
	private Texture img;
	private Texture myTexture;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTexRegionDrawable;
	private ImageButton button, button2;
	private Stage stage;
	private Slider slider;
	public BitmapFont font;

	public OptionScreen(GameObj game) {
		this.game = game;
		font = new BitmapFont();
		Skin skin = new Skin(Gdx.files.internal("uiskin.json")); 
		slider = new Slider(0, 100, 0.1f, false, skin);
		slider.setWidth(300);
		slider.setPosition(400, 500);

		// checkBox = new CheckBox("", skin);

	}

	@Override
	public void show() { // first to get called

		batch = new SpriteBatch();
		// img = new Texture("badlogic.jpg");
		myTexture = new Texture(Gdx.files.internal("back_button.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button = new ImageButton(myTexRegionDrawable); // Set the button up
		button.setSize(200, 100);
		button.setPosition(0, Gdx.graphics.getHeight() - 90);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("back");
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game)); // jumps the game screen

			}
		});

		slider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (slider.isDragging()) {
					//game.music.setVolume(slider.getValue() / 100f); // Controls music volume

				}

			}
		});

//		checkBox.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				 Gdx.graphics.setContinuousRendering(checkBox.isChecked());
//			}
//		});

		stage = new Stage(new ScreenViewport()); // Set up a stage for the ui
//		/stage.addActor(checkBox);
		stage.addActor(slider);
		stage.addActor(button); // Add the button to the stage to perform rendering and take input.
		Gdx.input.setInputProcessor(stage); // Start taking input from the ui

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("Option Page");

		batch.begin();
		font.draw(batch, "Music Volume", 250, 520);
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
