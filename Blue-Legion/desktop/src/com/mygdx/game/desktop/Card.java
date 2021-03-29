package com.mygdx.game.desktop;

 
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.g2d.TextureRegion; 
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton; 
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
 
/*
 * fetching the image of the card 
 */
public class Card implements ApplicationListener {
	// for button image
	private Texture myTexture;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTexRegionDrawable;
	private ImageButton card;
	private String name; 

	public Card(String name) {
		this.name = name; // fetch the name of the character

		// card represented as a button
		myTexture = new Texture(Gdx.files.internal(name +".png")); //gets the image from assets 
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		card = new ImageButton(myTexRegionDrawable); // Set the card up
		card.setPosition(100, 30);
	}
 
	// return card
	public ImageButton getCard() {
		return card;
	}

    //sets position of the card 
	public void setPosition(int x, int y) {
		card.setPosition(x, y);

	}
    //size of the card can be changed 
	public void setSize(int width, int height) {
		card.setSize(width, height);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
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
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

}
