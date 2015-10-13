package com.vojin.go.breakfree.domain.entities;



import java.io.File;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.vojin.go.breakfree.domain.repository.LocationRepository;
import com.vojin.go.breakfree.navigation.Coordinate;
import com.vojin.go.breakfree.navigation.Direction;
import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.ConfiguratioProps;
import com.vojin.go.breakfree.utils.ObjectBinder;
import com.vojin.go.breakfree.utils.RepositoryException;

/**
 * This class deals with the player and all of its properties.
 * @author Vojin Nikolic
 *
 */
@XmlRootElement
public class Player extends CreatureEntity {

	private int maxHitPoints;
	private int numPotions;
	private String sex;
	private int experience;
	
	private final Random random = new Random();
	
	private Location currentLocation;

	protected static LocationRepository locationRepo;

	/**
	 * Parameterized Constructor
	 * @param name
	 * @param description
	 * @param health
	 * @param minDamage
	 * @param maxDamage
	 * @param numPotions
	 * @param repo
	 */
	public Player(String name, String description, int health, int minDamage, int maxDamage, int numPotions, LocationRepository repo) {
		super(name, description, health, minDamage, maxDamage);
		this.maxHitPoints = health;
		this.numPotions = numPotions;
	}
	
	/**
	 * non parameterized Constructor
	 */
	public Player() {
		super();
		this.maxHitPoints = 40;
		this.numPotions = 10;
	}

	/**
	 * @return the numPotions
	 */
	public int getNumPotions() {
		return numPotions;
	}

	/**
	 * @param numPotions
	 *            the numPotions to set
	 */
	@XmlElement(name = "numpotions")
	public void setNumPotions(int numPotions) {
		this.numPotions = numPotions;
	}

	/**
	 * @return the maxHitPoints
	 */
	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	/**
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}

	public boolean isAlive() {
		return health > 0;
	}

	public String getStatus() {
		return "Player HP: " + health;
	}

	/**
	 * @return the locationRepo
	 */
	public LocationRepository getLocationRepo() {
		return locationRepo;
	}

	/**
	 * @param locationRepo
	 *            the locationRepo to set
	 */
	@XmlTransient
	public void setLocationRepo(LocationRepository locationRepo) {
		this.locationRepo = locationRepo;
	}

	/**
	 * @return the currentLocation
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation
	 *            the currentLocation to set
	 */
	@XmlElement(name = "currentLocation")
	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
		/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	@XmlElement(name = "sex")
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the experience
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	@XmlElement(name = "experience")
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * @param maxHitPoints the maxHitPoints to set
	 */
	@XmlElement(name = "maxpoints")
	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	/**
	 * This method prints all the possible exits {@link LocationRepository#getPossibleExits(Coordinate)} form current players location
	 * 
	 */
	public void printPossibleExits() {
		Communicator.provide("Possible exits : ");
		Map<Direction, Location> possibleExits = locationRepo.getPossibleExits(currentLocation.getCoordinate());
		for (Map.Entry<Direction, Location> directionExits : possibleExits.entrySet()) {
			Communicator.provide(directionExits.getKey().getDescription() + ": ");
			Communicator.provide("    " + directionExits.getValue().getTitle());
		}
		Communicator.provide("");
	}

	/**
	 * This method is responsible for attack action
	 * @return
	 * @see
	 */
	public int attack() {
		return random.nextInt(maxDamage - minDamage + 1) + minDamage + experience;
	}

	/**
	 * This method is responsible for defending from {@link Zombie}
	 * @param zombie
	 * 
	 */
	public void defend(Zombie zombie) {
		int attackStrength = zombie.attack();
		attackStrength = attackStrength - experience;
		health = (health > attackStrength) ? health - attackStrength : 0;
		Communicator.provide("  " + name + " is hit for " + attackStrength + " HP of damage " +  getStatus() + "\n");
		if (health == 0) {
			Communicator.provide("  " + name + " has been defeated");
		}
	}
	
	/**
	 * This method is responsible for healing after attack form {@link Zombie}
	 *
	 */
	public void heal() {
		if (numPotions > 0) {
			health = Math.min(maxHitPoints, health + 20);
			Communicator.provide(name +   "  drinks healing potion " +  getStatus() +  " potions left " +  --numPotions +  "\n");
		} else {
			Communicator.provide("  You've exhausted your potion supply!");
		}
	}
	
	/**
	 * Retrieves Player file path 
	 * @param name
	 * @return
	 * @see
	 */
	public String getPlayerFileName(String name) {
		return ConfiguratioProps.PLAYER_LOCATION.getDescription() + name + "/" + "profile.xml";
	}

	/**
	 * This method is responsible for loading all player data
	 * @param name
	 * @return
	 * @throws RepositoryException 
	 * @see
	 */
	public Player load(String name) throws RepositoryException {
		Player playerToReturn = null;
		try {
			String fileName = getPlayerFileName(name);
			File fileLocation = new File(fileName);
			ObjectBinder objectBinder = new ObjectBinder();
			playerToReturn = objectBinder.convertXMLToObject(Player.class, fileLocation);
		} catch (JAXBException ex) {
			throw new RepositoryException("Player data can't be loaded");
		}

		return playerToReturn;
	}
    
    /**
     * This method is responsible for saving all player data
     * @throws RepositoryException 
     * 
     * @see
     */
    public void savePlayer(Player player) throws RepositoryException {
    	Player playerToSave = player;
        try {
            String fileName = getPlayerFileName(getName());
            new File(fileName).getParentFile().mkdirs();
            File fileLocation = new File(fileName);
        	
            JAXBContext context = JAXBContext.newInstance(playerToSave.getClass());
            Marshaller mar = context.createMarshaller();
            mar.marshal(playerToSave, fileLocation);
            Communicator.provide("*** Game data has been saved ***");
        } catch (JAXBException e) {
        	throw new RepositoryException("Unable to save game data to file");
		}
    }
	
    public String getStats() {
    	 String message = "\nPlayer name: " + getName();
    	 message += "\nDescription: " + getDescription();
    	 message += "\nSex: " + getSex();
         message += "\nHealth/Max: " + getHealth() + "/" + "40";
         message += "\nNumber of potions: " + getNumPotions() + "/10";
         message += "\nExperience: " + getExperience();
  
         return message;
    }
    
    /**
     * Setting experiance per sex
     * 
     * @see
     */
	public void gainExperience() {
		if (getSex().equals("male")) {
			setExperience(experience + 1);
		} else {
			setExperience(experience + 2);
		}
	}
    
	@Override
	public String toString() {
		return name;
	}
}
