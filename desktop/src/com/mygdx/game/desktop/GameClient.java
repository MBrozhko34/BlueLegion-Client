package com.mygdx.game.desktop;

import java.net.URI;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameClient extends WebSocketClient {
	
	
	private CombatUI gameUI = null;
	private int playerNum = 0;
	private boolean myTurn = false;
	private String mes;
	public boolean OHB = false;
	
	public GameClient(URI serverUri, CombatUI gameUI) {
		super(serverUri);
		this.gameUI = gameUI;
		
		// TODO Auto-generated constructor stub
	}
	
//	public GameClient(URI serverUri) {
//	    super(serverUri, new Draft_6455());
//	}
//	
	//Use  .send(String)  method for sending stuff to server

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		// TODO Auto-generated method stub
		System.out.println("Connection Opened");
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}
	
	@Override
	public void onMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Message received: " + message);
		String delims = "[ ]+";
		String[] tokens = message.split(delims);
		if(tokens.length > 0) {
			mes = tokens[0];
			if(tokens[0].equals("YOUR_TURN")) {
				// ToDo: enable action button
				myTurn = true;
			} else if(tokens[0].equals("OTHER_TURN")) {
				// ToDo: disable action button
				myTurn = false;
			} else if(tokens[0].equals("NEW_HEALTH")) {
				int newHealth = Integer.parseInt(tokens[1]);
				System.out.println(" ");
				System.out.println("Health: "+newHealth);
				System.out.println(" ");
				gameUI.setPlayerHealth(playerNum, newHealth);
			} else if(tokens[0].equals("OTHER_HEALTH")) {
				int otherHealth = Integer.parseInt(tokens[1]);
				int otherPlayerNum = 1;
				if(playerNum == 1) {
					otherPlayerNum = 2;
				}
				gameUI.setPlayerHealth(otherPlayerNum, otherHealth);
			} else if(tokens[0].equals("YOU_ARE_PLAYER")) {
				playerNum = Integer.parseInt(tokens[1]);
			} else if(tokens[0].equals("GAME_WON")) {
				gameUI.gameWon();
				myTurn = false;
			} else if(tokens[0].equals("GAME_LOST")) {
				gameUI.gameLost();
				myTurn = false;
			} else if(tokens[0].equals("GAME_OVER")) {
				//gameUI.gameOver();
				myTurn = false;
			} else {
				System.out.println("Unexpected message recieved");
			}
		} else {
			System.out.println("Empty message recieved");
		}
		
	}
	
	public String getMessage() {
		return mes;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// TODO Auto-generated method stub
		System.out.println("Connection closed");
		
	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("Error communicating with server: ");
		ex.printStackTrace();
	}

}
