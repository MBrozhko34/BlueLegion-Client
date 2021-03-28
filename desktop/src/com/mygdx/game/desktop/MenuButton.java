package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuButton implements Screen {

	private Music music;
	private SpriteBatch batch;
	private Texture img;
	private Texture myTexture, myTexture2;
	private TextureRegion myTextureRegion, myTextureRegion2;
	private TextureRegionDrawable myTexRegionDrawable, myTexRegionDrawable2;
	private ImageButton button, button2, button3, button4, button5;
	private Stage stage, stage2;
	Table table;
	Slider slider;

	public MenuButton() {
		music = Gdx.audio.newMusic(Gdx.files.internal("fantasy.mp3"));
//		music.play();
		music.setLooping(true);
		// batch = new SpriteBatch();
		// img = new Texture("badlogic.jpg");
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		slider = new Slider(0, 100, 0.1f, false, skin); 

		slider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (slider.isDragging()) {
					  music.setVolume(slider.getValue() / 100f); // Controls music volume

				}

			}
		});
//menu button 
		table = new Table();
		table.setVisible(false);
		stage2 = new Stage();
		myTexture = new Texture(Gdx.files.internal("menu_button.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button = new ImageButton(myTexRegionDrawable); // Set the button up 
		button.setPosition(0, Gdx.graphics.getHeight() - 60);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				table.setVisible(true); // 2
			}
		});

		// popup
		myTexture = new Texture(Gdx.files.internal("on_button.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button2 = new ImageButton(myTexRegionDrawable);
		button2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				    music.play();
			}
		});
		myTexture = new Texture(Gdx.files.internal("off_button.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button3 = new ImageButton(myTexRegionDrawable); // Set the button up

		button3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
 
				  music.stop();
		 
			}
		});

		myTexture = new Texture(Gdx.files.internal("Close_button.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button5 = new ImageButton(myTexRegionDrawable); // Set the button up

		button5.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// System.out.println("going to pvp...");
//				((Game) Gdx.app.getApplicationListener()).setScreen(new OptionScreen(game));

				// System.exit(0); //exits
				// game.music.play();
				table.setVisible(false);
			}
		});

		table.setPosition(300, 300); // move the whole table
		table.setSize(400, 300);
		 
		table.add(slider).colspan(2);
		table.row();
		table.add(button2).padRight(10).padTop(50);

		table.add(button3).padTop(50);
		table.row();
		table.add(button5).colspan(2).padTop(40);
	

		//table.debug();
//	stage2.addActor(table);

//	table.setVisible(true);
		stage = new Stage(new ScreenViewport()); // Set up a stage for the ui
		//stage.addActor(button); // Add the button to the stage to perform rendering and take input.
		// stage.addActor(button4);
		//stage.addActor(table);
		// table.setVisible(false);
		//Gdx.input.setInputProcessor(stage); // Start taking input from the ui
	}

	@Override
	public void show() { // first to get called


	}
	public Table getTable() {
		return table;
	}
	public ImageButton getButton() {
		return button;
	}

	@Override
	public void render(float delta) {
	 
		stage.act();

		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		stage.dispose();

	}

}
