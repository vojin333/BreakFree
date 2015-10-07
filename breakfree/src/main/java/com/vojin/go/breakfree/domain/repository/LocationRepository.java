package com.vojin.go.breakfree.domain.repository;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vojin.go.breakfree.navigation.Coordinate;
import com.vojin.go.breakfree.navigation.Direction;
import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.navigation.LocationType;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.ConfiguratioProps;

/**
 * 
 * @author Vojin Nikolic
 * 
 * Repository class responsible to persist and load data from file system for {@link Location}
 *
 */
public class LocationRepository {

	
    private String fileName;
    private Map<Coordinate, Location> locations;

    /**
     * Parameterized Constructor
     * @param playerName
     */
	public LocationRepository(String playerName) {
		this.locations = new HashMap<Coordinate, Location>();
		fileName = ConfiguratioProps.PLAYER_LOCATION.getDescription() + playerName + "/locations.json";
		load();
	}
	
	private void load() {
		JsonParser parser = new JsonParser();
		File file = new File(fileName);
		if (!file.exists()) {
			copyLocationsFile();
		}
		try {
			Reader reader = new FileReader(fileName);
			JsonObject json = parser.parse(reader).getAsJsonObject();
			for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
				locations.put(new Coordinate(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
			}
			reader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Location loadLocation(JsonObject json) {
        Coordinate coordinate = new Coordinate(json.get("coordinate").getAsString());
        String title = json.get("title").getAsString();
        String description = json.get("description").getAsString();
        LocationType locationType = LocationType.valueOf(json.get("locationType").getAsString());
        boolean isCreatureAlive = json.get("isCeatureAlive").getAsBoolean();
        boolean isSeen = json.get("isSeen").getAsBoolean();
        Location location = new Location(coordinate, title, description, locationType, isCreatureAlive, isSeen);
        location.setDangerRating(json.get("danger").getAsInt());
		return location;
	}
	
	private void copyLocationsFile() {
		File source = new File(ConfiguratioProps.LOCATION_FILE.getDescription());
		System.out.println();
		File dest = new File(fileName);
		dest.mkdirs();
		try {
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveLocations() {
        try {
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Coordinate,Location> entryLocation : locations.entrySet()) {
                Location location = entryLocation.getValue();
                JsonObject locationJsonElement = new JsonObject();
                locationJsonElement.addProperty("title", location.getTitle());
                locationJsonElement.addProperty("coordinate", location.getCoordinate().toString());
                locationJsonElement.addProperty("description", location.getDescription());
                locationJsonElement.addProperty("locationType", location.getLocationType().toString());
                locationJsonElement.addProperty("isSeen", String.valueOf(location.isSeen()));
                locationJsonElement.addProperty("isCeatureAlive", String.valueOf(location.isCeatureAlive()));
                locationJsonElement.addProperty("danger", String.valueOf(location.getDangerRating()));
                jsonObject.add(location.getCoordinate().toString(), locationJsonElement);
            }
            Writer writer = new FileWriter(fileName);
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException ex) {
            Communicator.provide("Unable to save to file " + fileName);
        }
    }
	
	public void saveLocation(Location loactionToSave) {
		 for (Map.Entry<Coordinate,Location> entryLocation : locations.entrySet()) {
			 if (entryLocation.getKey().equals(loactionToSave.getCoordinate())){
				 entryLocation.setValue(loactionToSave);
			 }
		 }
		 saveLocations();
    }
	
	public Location getLocation(Coordinate coordinate) {
		if (coordinate == null) {
            return null;
        }
        return locations.get(coordinate);
    }
	
	
    public Location getInitialLocation() {
        Coordinate coordinate = new Coordinate(0, 0);
        return getLocation(coordinate);
    }
	
	public Map<Direction, Location> getPossibleExits(Coordinate coordinate) {
		Map<Direction, Location> exits = new HashMap<Direction, Location>();
		Location borderingLocation;
		for (Direction direction : Direction.values()) {
			borderingLocation = getLocation(coordinate.getBorderingCoordinate(direction));
			if (borderingLocation != null) {
				exits.put(direction, borderingLocation);	
			}
		}
		return exits;
	}
}
