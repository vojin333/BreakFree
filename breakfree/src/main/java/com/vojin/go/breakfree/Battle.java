package com.vojin.go.breakfree;



import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.domain.entities.Zombie;
import com.vojin.go.breakfree.prompter.InstructionHandler;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.GameOverException;
import com.vojin.go.breakfree.utils.RepositoryException;

/**
 * 
 * @author Vojin Nikolic
 *
 * Class that simulates the battle 
 * Throws {@link GameOverException} in case player dies
 *
 */
public class Battle {

	public Battle(Player player, Zombie zombie) throws GameOverException, RepositoryException {
		Communicator.provide("You encounter " + zombie + ": " + zombie.getDescription() + "\n");
		Communicator.provide("Battle with " + zombie + " starts (" + player.getStatus() + " / " + zombie.getStatus() + ")");
		while (player.isAlive() && zombie.isAlive()) {
			Communicator.provide("Attack (a) or heal (h)? ");
			String action = Communicator.accept();
			InstructionHandler parser  = new InstructionHandler(player);
			parser.parse(player, action);
			if (zombie.isAlive()) {
				player.defend(zombie);
			}
			if (!player.isAlive()) {
				Communicator.provide("Zombie have killed you... Start again? (y/n)");
				String reply = Communicator.accept().toLowerCase();
				
				while (!reply.startsWith("y") && !reply.startsWith("n")) {
					Communicator.provide("You died... Start again? (y/n)");
					reply = Communicator.accept().toLowerCase();
				}
				if (reply.startsWith("y")) {
					throw new GameOverException("again");
				} else if (reply.startsWith("n")) {
					throw new GameOverException("close");
				}
			}
		}
	}
}
