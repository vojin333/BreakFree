package com.vojin.go.breakfree;



import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.prompter.InstructionHandler;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.CreatureFactory;
import com.vojin.go.breakfree.utils.GameOverException;

/**
 * 
 * @author Vojin Nikolic
 * 
 * This class contains the main loop that takes all gameplay inputs
 *
 */
public class GameWorld {

	public InstructionHandler parser;
	Player player = null;

	public GameWorld(Player player, String playerType) throws GameOverException {
		this.parser = new InstructionHandler(player);
		this.player = player;
		switch (playerType) {
		case "new":
			printIntro();
			Communicator.provide("Your Current location is :");
			player.getCurrentLocation().print();
			player.getLocationRepo().saveLocation(player.getCurrentLocation());
			continueQuest();
			break;
		case "old":
			Communicator.provide("Welcome back, " + player.getName() + "!");
			Communicator.provide("");
			Communicator.provide("Your Current location is :");
			player.getCurrentLocation().print();
			continueQuest();
			break;
		default:
			Communicator.provide("Invalid player type");
			break;
		}
	}

    /**
     * This is the main loop for the player gaming
     * {@link InstructionHandler} checks if it can recognise a command
     *
     * 
     */
	public void continueQuest() throws GameOverException {
		boolean continueGame = true;
		try {
			while (continueGame) {
				if (player.getCurrentLocation().isCeatureAlive()) {
					player.getCurrentLocation().setZombie(CreatureFactory.createCreature());
					Communicator.provide(player.getCurrentLocation().getZombie().getDescription() + " and it is crwaling towards you");
					Communicator.provide(" If you wish to fight a zombie type 'fight'");
				}
				Communicator.provide("If you wish to explore further(go direction)");
				Communicator.provide("Possible exits : ");
				player.printPossibleExits();

				Communicator.provide("What is your next move:");
				String action = Communicator.accept();
				continueGame = parser.parse(player, action);
			}
		} catch (GameOverException e) {
			if (e.getGameMessage().equals("again")) {
				//if 'again' return to the current player position and restart
				return;
			} else {
				throw e;
			}
		}

	}

	private void printIntro() {
		Communicator.provide("2015 late summer. Dum dum dum dum dum dum sound of the heartbeat");
		Communicator.provide("That is the only sound present. Everything is blurry.");
		Communicator.provide("Hospital room is small and dirty. Some smell was getting from outside the small window.");
		Communicator.provide("Next to a bed there is a chair with all the clothes,everything is there, even a police badge, gun and a teaser.");
		Communicator.provide("A strange whisper,far away from this room, who is there...? Where is Doctor?Where are everybody?");
		Communicator.provide("(i) grabbed the dusty lock");
	}
}
