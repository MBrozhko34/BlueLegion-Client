package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private Texture sprite;
	private String name;
	private String health;
	private String[] actions = new String[4];
	private String action1, action2, action3, action4;

	private String attackStat;
	private String defenceStat;
	private String equipment;

	@Override
	public void create() {

		batch = new SpriteBatch();
//		img = new Texture(Gdx.files.internal("Goblin right.png"));
		img = new Texture(Gdx.files.internal("Goblin right.png"));

		FileHandle handle = Gdx.files.internal("data/glare.txt");
		// System.out.println(handle.readString());

		File file = new File("data/goblin.csv");

		try (Scanner sc = new Scanner(file)) {
			// sc.nextLine();
//			String []data = null;
//			String line = sc.nextLine();
			// a way to skip the first row
//			/sc.next();
			sc.nextLine();
			while (sc.hasNext()) {
				
				String line = sc.nextLine();
				String[] data = line.split(","); 

				// System.out.println(sc.hasNext());
				this.name = data[0];
				// converting int to string then placed in data array which is a string
				this.health = String.valueOf(data[1]);
				this.attackStat = String.valueOf(data[2]);
				this.defenceStat = String.valueOf(data[3]);

				// continues on
				this.actions[0] = data[4];
				this.actions[1] = data[5];
				this.actions[2] = data[6];
				this.actions[3] = data[7];
				this.equipment = data[8];
				this.sprite = new Texture(Gdx.files.internal(data[9]));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}



	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
