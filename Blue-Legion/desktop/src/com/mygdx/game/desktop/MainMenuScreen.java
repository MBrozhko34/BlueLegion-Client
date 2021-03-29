package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; 
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton; 
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen { // where the button will be

	private GameObj game;
	// textures for the buttons can be added here
	private Texture myTexture;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTexRegionDrawable;
	private ImageButton button, button2;
	private Stage stage;
	private SpriteBatch batch;
	private Texture logo;
	private Table table;
	private String label;
	private BitmapFont yourBitmapFontName;
	private Player player1, player2;


	public MainMenuScreen(GameObj game) {
		this.game = game;
	}

	@Override
	public void show() {
		logo = new Texture("logo.png");
		batch = new SpriteBatch(); // used to draw images in render
		table = new Table(); //where the buttons are stored in 
		
		// pvp button
		myTexture = new Texture(Gdx.files.internal("pvp.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button = new ImageButton(myTexRegionDrawable); // Set the button up
		 

		table.add(button);
		table.row();
		button.padBottom(10);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player1=new Player("goblin_left");
				player2=new Player("vampire_right");
				((Game) Gdx.app.getApplicationListener()).setScreen(new CombatUI(game, player1, player2));
			}
		});
		
		// option button
		myTexture = new Texture(Gdx.files.internal("Exit_button.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button2 = new ImageButton(myTexRegionDrawable); // Set the button up
		table.add(button2);
		table.row();
		button2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 System.exit(0);
			}
		});

		
		stage = new Stage(new ScreenViewport()); // Set up a stage for the ui

		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//table.debug();// check where the buttons are in the table

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage); // Start taking input from the ui

	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("Menu");
		
		batch.begin();
		batch.draw(logo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
		stage.dispose();
	}
 
}
