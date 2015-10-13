package com.vojin.go.breakfree;



import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.prompter.InstructionHandler;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.CreatureFactory;
import com.vojin.go.breakfree.utils.GameOverException;
import com.vojin.go.breakfree.utils.RepositoryException;

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

	public GameWorld(Player player, String playerType) throws GameOverException, RepositoryException {
		this.parser = new InstructionHandler(player);
		this.player = player;
		switch (playerType) {
		case "new":
			printIntro();
			player.getCurrentLocation().print();
			player.getLocationRepo().saveLocation(player.getCurrentLocation());
			continueQuest();
			break;
		case "old":
			Communicator.provide("Welcome back, " + player.getName() + "!");
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
     * {@link InstructionHandler} checks if it can recognize a command
     * @throws RepositoryException 
     *
     * 
     */
	public void continueQuest() throws GameOverException, RepositoryException {
		boolean continueGame = true;
		try {
			while (continueGame) {
				if (player.getCurrentLocation().isCeatureAlive()) {
					if (player.getCurrentLocation().isFinal()) {
						player.getCurrentLocation().setZombie(CreatureFactory.createFinalCreature());
						Communicator.provide(player.getCurrentLocation().getZombie().getDescription());
					} else {
						player.getCurrentLocation().setZombie(CreatureFactory.createCreature());
						Communicator.provide(player.getCurrentLocation().getZombie().getDescription() + " and it is crwaling towards you");
					}
					Communicator.provide("If you wish to fight a zombie type 'fight'");
					
				} else {
					Communicator.provide("There is no zombie in this room");
				}
				Communicator.provide("\nIf you wish to explore further (type 'go' and first letter of direction i.e: 'go n' to go north)");
				player.printPossibleExits();

				Communicator.provide("Type 'help' to see all possible commands");
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
		} catch (RepositoryException e) {
			throw e;
		}

	}

	private void printIntro() {
		Communicator.provide("2015 late summer. Dum dum, dum dum, dum dum (sound of the heartbeat)");
		Communicator.provide("That is the only sound present. Everything is blurry.");
		Communicator.provide("Hospital room is small and dirty. Some smell was getting from outside the small window.");
		Communicator.provide("Next to a bed there is a chair with all the clothes,everything is there, even a police badge, gun and a teaser.");
		Communicator.provide("A strange whisper,far away from this room, who is there...? Where is Doctor?Where are everybody?");
		Communicator.provide("(i) grabbed the dusty lock");
	}
}
