package com.vojin.go.breakfree.menus;



import java.io.File;

import com.vojin.go.breakfree.BreakFree;
import com.vojin.go.breakfree.GameWorld;
import com.vojin.go.breakfree.dependency.BeanFactory;
import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.ConfiguratioProps;
import com.vojin.go.breakfree.utils.GameOverException;
import com.vojin.go.breakfree.utils.RepositoryException;

/**
 * 
 * @author Vojin Nikolic
 * 
 * This is initial game menu 
 * @see BreakFree
 *
 */
public class MainMenu extends Menus implements Runnable {
     
    public MainMenu() {
    	startMenu();
    	
    }
    
    public void run() {
    	startMenu();
    }

	public void startMenu() {
		this.menuItems.add(new MenuItem("Start", "Start a new Game", "new"));
		this.menuItems.add(new MenuItem("Load", "Loads an existing Game"));
		this.menuItems.add(new MenuItem("Exit", null, "quit"));

		while (true) {
			try {
				MenuItem selectedItem = displayMenu(this.menuItems);
				boolean exitGame = selectAnItem(selectedItem);
				if (!exitGame) {
					break;
				}
			} catch (GameOverException e) {
				//if 'close' exit game
				if (e.getGameMessage().equals("close")) {
					break;
				}
			} catch (RepositoryException e) {
				Communicator.provide("There is a problem with starting game... Reason : " + e.getRepositoryMessage()) ;
				Communicator.provide("Please try reinstalling the game");
			}
		}
		Communicator.provide("Game will exit...Bye!");
	}

	private static boolean selectAnItem(MenuItem menuItem) throws GameOverException, RepositoryException{
		String playerNameToLoad = menuItem.getInstructionCommand();
		switch (playerNameToLoad) {
		case "start":
			Communicator.provide("\nWhat is the name of the avatar you want to start with? ");
			String newPlayerName = Communicator.accept();
			while (isPlayernameTaken(newPlayerName)) {
				Communicator.provide("\nPlayer with " + newPlayerName + " aleready exists! Please type another avatar name");
				newPlayerName = Communicator.accept();
			}
			new CreateCharacterMenu(newPlayerName);
			break;
		case "exit":
			return false;
		case "load":
			displayListOfProfiles();
			Player player = new Player();
			boolean exit = false;
			while (player.getName() == null) {
				playerNameToLoad = Communicator.accept();
				if (isPlayernameTaken(playerNameToLoad)) {
					player.setLocationRepo(BeanFactory.createLocationRepository(playerNameToLoad));
					player = player.load(playerNameToLoad);
				} else if ("exit".equals(playerNameToLoad) || "back".equals(playerNameToLoad) ) {
					exit = true;
					break;
				} else {
					Communicator.provide("That user doesn't exist. Try again.");
				}
			}
			if (exit) {
				return true;
			}
			new GameWorld(player, "old");
			break;
		}
		return true;
	}

    /**
     * This method displays all the Players name
     * 
     * @see
     */
	private static void displayListOfProfiles() {
		File file = new File(ConfiguratioProps.PLAYER_LOCATION.getDescription());
		String[] players = file.list();
		if (players.length != 0) {
			Communicator.provide("Players:");
			int i = 1;
			for (String name : players) {
				if (new File(ConfiguratioProps.PLAYER_LOCATION.getDescription()).isDirectory()) {
					Communicator.provide("  " + i + ". " + name);
				}
				i += 1;
			}
			Communicator.provide("\n  Type whole name of the player you want to load? Type 'back' to go back");
		} else {
			Communicator.provide("There are no players saved yet... Type 'back' to go back" );
		}

	}
	
	/**
	 * This method checks if playername is already taken
	 * @param palyerName
	 * @return
	 * @see
	 */
	private static boolean isPlayernameTaken(String palyerName) {
		File file = new File(ConfiguratioProps.PLAYER_LOCATION.getDescription());
		String[] playersList = file.list();
		if (playersList != null) {
			for (String player : playersList) {
				if (player.equals(palyerName)) {
					return true;
				}
			}
		}
		return false;
	}
}
