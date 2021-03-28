package com.mygdx.game.desktop;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.text.View;

import org.java_websocket.client.WebSocketClient;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CombatUI implements Screen {

	private SpriteBatch batch;
	// player 1
	private SpriteBatch healthbar2;
	// player 2
	private SpriteBatch healthbar1;
	private BitmapFont font;
	private Player player1;
	// This is going to be a 'dummy' which is going to take the place of
	// player 2 until the network features have been implemented
	private Player player2;
	private String random1;
	private String random2;
	private ShapeRenderer shape;
	private Texture logo;
	private String p1;
	private String p2;
	private Background backgroundBuild;
	private Texture background;
	private GameObj game;
	private Stage stage;
	private Card attack;
	private Card healthCard;
	private Card basic_attack;
	private MenuButton menuButton;
	private int r = ThreadLocalRandom.current().nextInt(4, 8);
	// private int health = 100;
	private Texture blank;
	
	private Character character1 = null;
	private Character character2 = null;
	
	private GameClient gameClient = null;
	
	//private String serverLocation = "ws://192.168.56.101:8887";
	private String serverLocation = "ws://localhost:8887";
	//private String serverLocation = "ws://ec2-3-8-233-150.eu-west-2.compute.amazonaws.com:8887";
	
	private GameOver gO = null;
	private GameWon gW = null;
	private GameLost gL = null;
	
	private boolean wantSeeP1;
	private boolean wantSeeP2;
	
	Effects goblinAttack = new Effects("goblinAttack",4,4);
	Effects vampireAttack = new Effects("slashVampire",4,4);
	
	Effects heal1 = new Effects("heal",4,4);
	Effects heal2 = new Effects("healRed",4,4);
	
	boolean p1Attacked = false;
	boolean p2Attacked = false;
	
	boolean p1healed = false;
	boolean p2healed = false;
	
	private double p1Pos = Math.round(Gdx.graphics.getWidth()*0.05);
	private double p2Pos = Math.round(Gdx.graphics.getWidth()*0.8);
	
	private int n;
	
	private int gP=200;

	
	// takes the two players characters as paramaters and creates the relevant
	// assets
	// public CombatUI(GameObj game, String player1Char, String player2Char) {
	public CombatUI(GameObj game, Player player1, Player player2) {
		this.game = game; // needed to display screen
		// this.p1 = player1Char;
		// this.p2 = player2Char;
		this.backgroundBuild = new Background();
		// this.player1 = new Player(player1Char);
		// this.player2 = new Player(player2Char);
		this.player1 = player1;
		this.player2 = player2;
		// this.effect = new Effect();
		menuButton = new MenuButton();
		
		gO = new GameOver(game);
		gW = new GameWon(game);
		gL = new GameLost(game);
		
		try {
			gameClient = new GameClient(new URI(serverLocation), this);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		character();
 
	}
	
	public void character() {
		character1 = new Character("goblin_left");
		character1.position(-100, 150);
		character1.resize(400, 400);
		
		character2 = new Character("vampire_right");
		character2.position(600, 150);
		character2.resize(600, 600);
	}
	
	public void moveAnimation() {
		if(n<600) {
			while(n<600) {
				n++;
			}
		} else {
			while(n>0) {
				n--;
			}
		}
	}
	
	public void setPlayerHealth(int pN, int pH) {
		if(pN == 1) {
			if(pH>player1.getPHealth()) {
				p2Attacked=false;
				p1Attacked=false;
				p2healed=false;
				p1healed=true;
			} else if(pH<player1.getPHealth()) {
				p2Attacked=false;
				p1healed=false;
				p2healed=false;
				p1Attacked=true;
			}
			player1.setHealth(pH);
		} else if(pN == 2) {
			if(pH>player2.getPHealth()) {
				p2Attacked=false;
				p1Attacked=false;
				p1healed=false;
				p2healed=true;
			} else if(pH<player2.getPHealth()) {
				p1Attacked=false;
				p1healed=false;
				p2healed=false;
				p2Attacked=true;
			}
			player2.setHealth(pH);
		} else {
			System.out.println("Unexpected player number: " + pN);
		}
	}
	
	
	public void gameWon() {
		Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
            	((Game) Gdx.app.getApplicationListener()).setScreen(gW);
            }
        });	
	}
	
	public void gameLost() {
		Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
            	((Game) Gdx.app.getApplicationListener()).setScreen(gL);
            }
        });	
	}
	
	public void gameOver() {
		Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
            	((Game) Gdx.app.getApplicationListener()).setScreen(gO);
            }
        });	
	}
	
	// @Override
	public void show() {

		blank = new Texture("blank.png"); // score using color to change health, png allows to change size and white

		batch = new SpriteBatch();
		// player 2
		healthbar1 = new SpriteBatch();
		// player 1
		healthbar2 = new SpriteBatch();

		font = new BitmapFont();
		shape = new ShapeRenderer();
		this.background = new Texture(Gdx.files.internal("grave.png")); // .getBackground("grave.png");
		logo = new Texture(Gdx.files.internal("logo.png"));
		// player1 = new Player(this.p1);
		// Gets the first random attack text
		this.random1 = player1.getRandomAction();

		// player2 = new Player(this.p2);
		// Gets the first random attack text
		this.random2 = player2.getRandomAction();

		// showing the three card on screen

		basic_attack = new Card("basicAttack_card"); // left card
		basic_attack.setPosition((int) Math.round(Gdx.graphics.getWidth()*0.15), 3);
		attack = new Card("attack_card"); // middle card
		attack.setPosition((int) Math.round(Gdx.graphics.getWidth()*0.4), 5);
		healthCard = new Card("health_card"); // right card
		healthCard.setPosition((int) Math.round(Gdx.graphics.getWidth()*0.65), 5);
		
		// adding the card to stage
		stage = new Stage(new ScreenViewport()); // Set up a stage for card to be displayed
		stage.addActor(attack.getCard());
		stage.addActor(healthCard.getCard());
		stage.addActor(basic_attack.getCard());
		
		stage.addActor(menuButton.getButton());
		stage.addActor(menuButton.getTable());
		Gdx.input.setInputProcessor(stage); // Start taking input from the ui
		
		// connects to the server
		if(gameClient != null) {
			gameClient.connect();
		}
		
		if (p1healed == true) {
			heal1.create();
		}
		else if (p2healed == true) {
			heal2.create();
		}
		
		if (p1Attacked == true) {
			goblinAttack.create();
		}
		else if (p2Attacked == true) {
			vampireAttack.create();
		}
		
		
		character1.create(); 
		character2.create();
		
		attack.getCard().addListener(new ClickListener() {
			// middle card
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				if(gameClient != null && gameClient.isMyTurn()) { 
					gameClient.send("ATTACK"); 
					
					if(gameClient.getPlayerNum() == 1) {
						p2Attacked = false; 
						p1Attacked = true; 
					} else if(gameClient.getPlayerNum() == 2){
						p1Attacked = false;
						p2Attacked = true; 
					} else {
						System.out.println("error: no player turn");
					}
					
				} else {
					System.out.println("Not my turn");
				}
				
			}
		});
		
		// gains health for player 1
		healthCard.getCard().addListener(new ClickListener() {
			// last card (health)
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				if(gameClient != null && gameClient.isMyTurn()) { 
					gameClient.send("BOOST");

					if(gameClient.getPlayerNum() == 1) {
						p2Attacked=false;
						p1Attacked=false;
						p2healed=false;
						p1healed=true;
					} else if(gameClient.getPlayerNum() == 2){
						p2Attacked=false;
						p1Attacked=false;
						p1healed=false;
						p2healed=true;					
					}else {
						System.out.println("error: no player turn");
					}
				} else {
					System.out.println("Not my turn");
				}
			}
		});
		
		//test card
		basic_attack.getCard().addListener(new ClickListener() {
			// last card (health)
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				if(gameClient != null && gameClient.isMyTurn()) { 
					gameClient.send("BASE_ATTACK"); 
					if(gameClient.getPlayerNum() == 1) {
						p2healed=false;
						p1healed=false;
						p1Attacked = false;
						p2Attacked = true; 
					} else if(gameClient.getPlayerNum() == 2){
						p2healed=false;
						p1healed=false;
						p2Attacked = false;
						p1Attacked = true; 
					} else {
						System.out.println("error: no player turn");
					}
					
				} else {
					System.out.println("Not my turn");
				}
			}
		});
	}
	public void showTurnP2() {
		wantSeeP1 = false;
		wantSeeP2 = true;
	}
	
	public void showTurnP1() {
		wantSeeP1 = true;
		wantSeeP2 = false;
	}
	
	
	// @Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// begins drawing things onto the screen
		batch.begin();
		// puts the bakcdrop onto the screen
		batch.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Draws the logo for Blue legion on the screen
		batch.draw(this.logo, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 250, 200, 200);
		// putting player 1's details onto the screen:

//player 1
		//font.draw(batch, "player 1", 150, Gdx.graphics.getHeight() - 70);
		
//		if(wantSeeP1) {
//			font.draw(batch, "YOUR TURN", 150, Gdx.graphics.getHeight() - 50);
//		}

		batch.draw(player1.getSprite(), (int) p1Pos, (int) Math.round(Gdx.graphics.getHeight()*0.20), 250, 330);
		
		/*
		 * // block card // ----------- // block command font.draw(batch, "Block",
		 * Gdx.graphics.getWidth() / 16, Gdx.graphics.getHeight() / 4); // action 1 card
		 * // ------- // action 1 command font.draw(batch, player1.getActions()[0],
		 * Gdx.graphics.getWidth() / 16 + 110, Gdx.graphics.getHeight() / 4); // action
		 * 2 card // ---------- // action 2 command font.draw(batch, this.random1,
		 * Gdx.graphics.getWidth() / 16 + 220, Gdx.graphics.getHeight() / 4); // putting
		 * player 2's details onto the screen // health // font.draw(batch, "Health:" +
		 * Integer.parseInt(player1.getHealth()), 150, // Gdx.graphics.getHeight() -
		 * 150);
		 */
		// name
		//font.draw(batch, "player 2", Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 70);
		
//		if(wantSeeP2) {
//			font.draw(batch, "YOUR TURN", Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50);
//		}
// player 2
		// sprite
		batch.draw(player2.getSprite(), (int) p2Pos, (int) Math.round(Gdx.graphics.getHeight()*0.20), 250, 400);
		// stops drawing things on the screen
		
		
		if(gameClient.getMessage() == "YOUR_TURN") {
			while(p1Pos < p2Pos) {
				p1Pos=p1Pos+5;
			}
		} else {
			
		}
		
		if(gameClient.isMyTurn()==true) {
			font.draw(batch, "YOUR TURN", (Gdx.graphics.getWidth()/2)-40, Gdx.graphics.getHeight() - 40);
		} else {
			font.draw(batch, "OTHER PLAYERS TURN", (Gdx.graphics.getWidth()/2)-80, Gdx.graphics.getHeight() - 40);
		}
		
		if(gameClient.getPlayerNum()==1) {
			font.draw(batch, "YOU", 50, Gdx.graphics.getHeight() - 70);
			font.draw(batch, "OTHER PLAYER", Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 70);
		} else if(gameClient.getPlayerNum()==2) {
			font.draw(batch, "YOU", Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 70);
			font.draw(batch, "OTHER PLAYER", 50, Gdx.graphics.getHeight() - 70);
		}
		
		healthBarPlayer1();
		healthBarPlayer2();
		
		if (p1healed == true) {
			heal1.position(0, 200);
			heal1.resize(300, 300);
			heal1.render();
		}
		if (p2healed == true) {
			heal2.position(700, 200);
			heal2.resize(300, 300);
			heal2.render();
		}
		if (p1Attacked == true) {
			vampireAttack.position(400, 200);
			vampireAttack.resize(400, 400);
			vampireAttack.render();
		}
		if (p2Attacked == true) {
			if (gP<700) {
				gP=gP+10;
				goblinAttack.position(gP, 220);
				goblinAttack.resize(150, 150);
				goblinAttack.render();
			} else {
				gP=100;
			}
		}
		
		character1.render();
		character2.render();
		
		stage.act(); // must be placed after batch to display cards
		stage.draw();

	}
	
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shape.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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

	// player1 on the left
	public void healthBarPlayer1() {
		// for player1
		healthbar1.begin();
		// colour of the health bar
		// 60%
		if (Integer.parseInt(player1.getHealth()) > player1.getOriginalHealth() * 0.6) {
			healthbar1.setColor(Color.GREEN);
			// 20%
		} else if (Integer.parseInt(player1.getHealth()) > player1.getOriginalHealth() * 0.2) {
			healthbar1.setColor(Color.ORANGE);
		} else {
			healthbar1.setColor(Color.RED);
		}
		// drawing the health bar
		healthbar1.draw(blank, (int) Math.round(Gdx.graphics.getWidth()*0.05), (int) Math.round(Gdx.graphics.getHeight()*0.875), 3 * Integer.parseInt(player1.getHealth()), 10);
		healthbar1.end();
	}

	// player2 on the left
	public void healthBarPlayer2() {
		healthbar2.begin();
		// colour of the health bar
		// 60%
		if (Integer.parseInt(player2.getHealth()) > player2.getOriginalHealth() * 0.6) {
			healthbar2.setColor(Color.GREEN);
			// 20%
		} else if (Integer.parseInt(player2.getHealth()) > player2.getOriginalHealth() * 0.2) {
			healthbar2.setColor(Color.ORANGE);
		} else {
			healthbar2.setColor(Color.RED);
		}
		

		// drawing the health bar
		healthbar2.draw(blank,(int) Math.round(Gdx.graphics.getWidth()*0.65), (int) Math.round(Gdx.graphics.getHeight()*0.875), 3 * Integer.parseInt(player2.getHealth()), 10);

		healthbar2.end();
		batch.end();

	}
}
