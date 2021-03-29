package com.mygdx.game.desktop;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Action {
	private HashMap<String, String[]> actionMap;

	public Action() {
		actionMap = new HashMap<String, String[]>();
	}

	// Takes the name of the action as a paramater and searches for the
	// file which contains the actions data and adds it to the hashmap
	// as a string array, with the key being the name of the attack
	public void addAction(String name) throws IOException {
		// creates a scanner to read the specified file
 		 Scanner reader = new Scanner(new File("data/" + name + ".txt")); 
			reader.nextLine();
			String[] data;
			data = new String[3];
			 int count=0;
			// adds the data found in that file to the data array debugging
			while (reader.hasNext()) {
				data[count] = reader.nextLine();
				System.out.println(data[count]);
				count++;
			}
			// places the data found in the array into the actionsMap
			actionMap.put(name, data);
			// debugging
			System.out.println("Added attack to the hashmap! \n");
		} 

	public String[] getAction(String name) {
		return actionMap.get(name);
	}
}
