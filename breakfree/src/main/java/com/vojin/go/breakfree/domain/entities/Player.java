package com.vojin.go.breakfree.domain.entities;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Random;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vojin.go.breakfree.domain.repository.LocationRepository;
import com.vojin.go.breakfree.navigation.Coordinate;
import com.vojin.go.breakfree.navigation.Direction;
import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.utils.Communicator;

/**
 * This class deals with the player and all of its properties.
 * @author Vojin Nikolic
 *
 */
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
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * @param maxHitPoints the maxHitPoints to set
	 */
	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	/**
	 * This method prints all the possible exits {@link LocationRepository#getPossibleExits(Coordinate)} form current players location
	 * 
	 */
	public void printPossibleExits() {
		Map<Direction, Location> possibleExits = locationRepo.getPossibleExits(currentLocation.getCoordinate());
		for (Map.Entry<Direction, Location> directionExits : possibleExits.entrySet()) {
			Communicator.provide(directionExits.getKey().getDescription() + ": ");
			Communicator.provide("    " + directionExits.getValue().getTitle());
		}
	}

	/**
	 * This method is responsible for attack action
	 * @return
	 * @see
	 */
	public int attack() {
		return random.nextInt(maxDamage - minDamage + 1) + minDamage;
	}

	/**
	 * This method is responsible for defending form {@link Zombie}
	 * @param zombie
	 * 
	 */
	public void defend(Zombie zombie) {
		int attackStrength = zombie.attack();
		health = (health > attackStrength) ? health - attackStrength : 0;
		Communicator.provide("  " + name + " is hit for " + attackStrength + " HP of damage " +  getStatus() + "\n");
		if (health == 0) {
			Communicator.provide("  " + name + " has been defeated");
		}
	}
	
	/**
	 * This method is responsible for healing afer attack form {@link Zombie}
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
		return "game_data/user_data/" + name + "/" + "profile.json";
	}

	/**
	 * This method is responsible for loading all player data
	 * @param name
	 * @return
	 * @see
	 */
    public Player load(String name) {
        Player player = new Player();
        JsonParser parser = new JsonParser();
        String fileName = getPlayerFileName(name);
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            player.setName(json.get("name").getAsString());
            player.setDescription(json.get("description").getAsString());
            player.setHealth(json.get("health").getAsInt());
            player.setMinDamage(json.get("minDamage").getAsInt());
            player.setMaxDamage(json.get("maxDamage").getAsInt());
            player.setNumPotions(json.get("numPotions").getAsInt());
            player.setExperience(json.get("experience").getAsInt());
            player.setSex(json.get("sex").getAsString());
            
            Coordinate coordinate = new Coordinate(json.get("location").getAsString());
            player.setCurrentLocation(locationRepo.getLocation(coordinate));
            reader.close();

        } catch (FileNotFoundException ex) {
            Communicator.provide( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return player;
    }
    
    /**
     * This method is responsible for saving all player data
     * 
     * @see
     */
    public void savePlayer() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("description", getHealth());
        jsonObject.addProperty("health", getHealth());
        jsonObject.addProperty("minDamage", getMinDamage());
        jsonObject.addProperty("maxDamage", getMaxDamage());
        jsonObject.addProperty("numPotions", getNumPotions());
        jsonObject.addProperty("experience", getExperience());
        jsonObject.addProperty("sex", getSex());
         
        
        Coordinate coordinate = getCurrentLocation().getCoordinate();
        String coordinateLocation = coordinate.x + "," + coordinate.y;
        jsonObject.addProperty("location", coordinateLocation);

        String fileName = getPlayerFileName(getName());
        new File(fileName).getParentFile().mkdirs();
        try {
            Writer writer = new FileWriter(fileName);
            gson.toJson(jsonObject, writer);
            writer.close();
            locationRepo.saveLocations();
        } catch (IOException ex) {
            Communicator.provide("\nUnable to save game data to file '" + fileName + "'.");
        }
    }
	
    /**
     * 
     * @param name
     * @return
     * @see
     */
	public boolean isPlayerExist(String name) {
		File file = new File(getPlayerFileName(name));
		return file.exists();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
