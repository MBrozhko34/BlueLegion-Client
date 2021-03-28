package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
//gives us the ability to create sprites
import com.badlogic.gdx.graphics.Texture;

import java.io.BufferedReader;
//gives us the ability to parse files
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Player {
	private Texture sprite;
	private String name;
	private String health;
	private String originalHealth;
	private String[] actions = new String[4];
	private int rounds;
	private Action hand;
	private String attackStat;
	private String defenceStat;
	private String equipment;
	private Action actionBuild;
	//private Effect effectBuild;
	
	private int nH = 100;

	// takes the name of the character as a parameter and parses the relevant
	// character file to get information
	public Player(String name) {
		InputStream file = this.getClass().getResourceAsStream("/" + name + ".csv");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
			// skip the first (header) row
			reader.readLine();
			
	        while (reader.ready()) {
	            String line = reader.readLine();
	            
				String[] data = line.split(",");

				// System.out.println(sc.hasNext());
				this.name = data[0];
				// converting int to string then placed in data array which is a string
				this.health = String.valueOf(data[1]);
				this.originalHealth = String.valueOf(data[1]);
				this.attackStat = String.valueOf(data[2]);
				this.defenceStat = String.valueOf(data[3]);

				// continues on
				this.actions[0] = data[4];
				this.actions[1] = data[5];
				this.actions[2] = data[6];
				this.actions[3] = data[7];
				
				//this.actionBuild.addAction(actions[0]);
				//this.actionBuild.addAction(actions[1]);
				//this.actionBuild.addAction(actions[2]);
				//this.actionBuild.addAction(actions[3]);
				// for (int count=0;count<this.actions.length-1;count++)
				// {System.out.println(this.actions[count]);this.actionBuild.addAction(this.actions[count]);}
				this.equipment = data[8];
				String fname = data[9];
				String path = Gdx.files.getLocalStoragePath();
				this.sprite = new Texture(Gdx.files.internal(fname));
				this.rounds=0;

	        }
	    }catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		
//		try (Scanner sc = new Scanner(file)) {
//
//			sc.nextLine(); // skips first row
//			while (sc.hasNext()) {
//
//				String line = sc.nextLine(); // main one that reads each line
//				String[] data = line.split(",");
//
//				// System.out.println(sc.hasNext());
//				this.name = data[0];
//				// converting int to string then placed in data array which is a string
//				this.health = String.valueOf(data[1]);
//				this.originalHealth = String.valueOf(data[1]);
//				this.attackStat = String.valueOf(data[2]);
//				this.defenceStat = String.valueOf(data[3]);
//
//				// continues on
//				this.actions[0] = data[4];
//				this.actions[1] = data[5];
//				this.actions[2] = data[6];
//				this.actions[3] = data[7];
//				
//				//this.actionBuild.addAction(actions[0]);
//				//this.actionBuild.addAction(actions[1]);
//				//this.actionBuild.addAction(actions[2]);
//				//this.actionBuild.addAction(actions[3]);
//				// for (int count=0;count<this.actions.length-1;count++)
//				// {System.out.println(this.actions[count]);this.actionBuild.addAction(this.actions[count]);}
//				this.equipment = data[8];
//				this.sprite = new Texture(Gdx.files.internal(data[9]));
//				this.rounds=0;
//
////				for (int count = 0; count <= 3; count++) {
////					this.actions[count] = data.split(",")[4 + count];
////					actionBuild.addAction(this.actions[count]);
////				}
//
//			}
//		}
	}

//	public ArrayList<String[]> getEffectList() {
//		return this.effectBuild.getEffectList();
//	}
	public int getRoundsWon() {return this.rounds;}
	public void resetRounds() {this.rounds=0;}
	public void addRound() {this.rounds +=1;}
	public void resetHealth() {this.health=this.originalHealth;}
	public Integer getOriginalHealth() {
		return Integer.parseInt(this.originalHealth);
	}

	public Action getActionBuild() {
		return this.actionBuild;
	}

	public Texture getSprite() {
		return this.sprite;
	}

	public String getName() {
		return this.name;
	}

	public String getHealth() {
		return this.health;
	}

	public void addHealth(int incrament) {
		this.health = Integer.toString(incrament += (int) Float.parseFloat(this.health));
	}
	
	public void setHealth(int newHealth) {
		nH=newHealth;
		this.health = Integer.toString(newHealth);
	}

	public int getPHealth() {
		return nH;
	}
	
	public String getAttackStat() {
		return this.attackStat;
	}

	public String getDefenceStat() {
		return this.defenceStat;
	}

	public String[] getActions() {
		return this.actions;
	}

	// returns a random attack
	public String getRandomAction() {
		Random random = new Random();
		return this.actions[random.nextInt(4 - 1) + 1];
	}

	/**
	 * retrieves a random action from the actions designated to that player
	 * 
	 * @return String[] containing the name of the action and attack value
	 * @throws FileNotFoundException 
	 */
	public String[] AI() throws FileNotFoundException {
		//String[] data=new String[4];
		int count=0;
		//String[] data = actionBuild.getAction(this.getRandomAction());
		Scanner reader= new Scanner(new File("data//"+getRandomAction()+".txt"));
		reader.nextLine();
		String data=reader.nextLine();
		String[] dataArray = data.split(",");
		reader.close();
		System.out.println("Supposed to be a number:"+dataArray[0]);
		return dataArray;
	}
	/**
	 * Returns a random attack based on all the actions available to the character
	 * @return String action
	 */
	public String getActionAI() {
		Random random = new Random();
		return this.actions[random.nextInt(4-1)+1];
	}
	/**
	 * parses the text data file that matches the name and returns the data found
	 * @param String action
	 * @return String[] actionData
	 * @throws FileNotFoundException
	 */
	public String[] getActionData(String action) throws FileNotFoundException {
		Scanner reader=new Scanner(new File("data//"+action+".txt"));
		reader.nextLine();
		String[] data=reader.nextLine().split(",");
		reader.close();
		return data;
	}
	// retrieves the data for the specified attack from the hashmap.
	// It has been done in this fashon because it is easier for us (the dev's) to
	// code "around" this
//	public Action getActionData(String name) {
//		return this.actionBuild.getActionData(name);
//	}
}
