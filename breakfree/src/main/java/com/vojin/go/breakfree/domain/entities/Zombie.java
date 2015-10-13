package com.vojin.go.breakfree.domain.entities;





import java.util.Random;

import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.RepositoryException;
/**
 * 
 * @author Vojin Nikolic
 * 
 * This class represents one of the creatures in the game
 *
 */
public class Zombie extends CreatureEntity{

    private final static Random random = new Random();
    
    /**
     * Parameterized Constructor
     * @param name
     * @param description
     * @param health
     * @param minDamage
     * @param maxDamage
     */
    public Zombie(String name, String description, int health, int minDamage, int maxDamage) {
        super(name, description, health, minDamage, maxDamage);
    	this.name = name;
        this.description = description;
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getStatus() {
        return "Zombie HP: " + health;
    }

    public int attack() {
        return random.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    /**
     * Method responsible for defending
     * @param player
     * @throws RepositoryException 
     */
	public void defend(Player player) throws RepositoryException {
		int attackStrength = player.attack();
		health = (health > attackStrength) ? health - attackStrength : 0;
		Communicator.provide(player + " hits " + name + " for " + attackStrength + " HP of damage " + getStatus() + "\n");
		if (health == 0) {
			player.getCurrentLocation().setCeatureAlive(false);
			try {
				player.getLocationRepo().saveLocation(player.getCurrentLocation());
				player.gainExperience();
				player.savePlayer(player);
				Communicator.provide("  " + player + " crashes the skull of a " + name + " zombie into a red stain");
				if (player.getCurrentLocation().isFinal()) {
					Communicator.provide("  "+ player.getDescription() + " " + player.getName() + " has managed to escape the Hospital !!!!!!");
					Communicator.provide(" ******   Congratulations  ******");
				}
			} catch (RepositoryException e) {
				throw e;
			}

		}
	}
	
    public boolean isAlive() {
        return health > 0;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
