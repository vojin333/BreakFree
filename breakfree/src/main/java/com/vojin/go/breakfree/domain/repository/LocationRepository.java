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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vojin.go.breakfree.navigation.Coordinate;
import com.vojin.go.breakfree.navigation.Direction;
import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.navigation.LocationType;
import com.vojin.go.breakfree.navigation.Locations;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.ConfiguratioProps;
import com.vojin.go.breakfree.utils.ObjectBinder;
import com.vojin.go.breakfree.utils.RepositoryException;

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
     * @throws RepositoryException
     */
	public LocationRepository(String playerName) throws RepositoryException{
		this.locations = new HashMap<Coordinate, Location>();
		fileName = ConfiguratioProps.PLAYER_LOCATION.getDescription() + playerName + "/locations.xml";
		loadLocationRepositoryData();
	}
	
	private void loadLocationRepositoryData() throws RepositoryException{
		File file = new File(fileName);
		if (!file.exists()) {
			copyLocationsFile();
		}
		try {
			File fileLocation = new File(fileName);
			ObjectBinder objectBinder = new ObjectBinder();
			Locations locationsList = objectBinder.convertXMLToObject(Locations.class, fileLocation);
			for (Location location : locationsList.getLocations()) {
				this.locations.put(location.getCoordinate(), location);
			}
		} catch (JAXBException jaxE) {
			// TODO fix exceptions
			jaxE.printStackTrace();
			throw new RepositoryException(jaxE.getMessage());
		}
	}

	private void copyLocationsFile() throws RepositoryException {
		try {
			File source = new File(ConfiguratioProps.LOCATION_FILE.getDescription());
			File dest = new File(fileName);
			dest.mkdirs();
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RepositoryException(e.getMessage());
			
		}
	}
	
	public void saveLocations() {
        try {
            JsonObject jsonObject = new JsonObject();
            ObjectBinder objectBinder = new ObjectBinder();
            File fileLocation = new File(fileName);
            for (Map.Entry<Coordinate,Location> entryLocation : locations.entrySet()) {
                Location location = entryLocation.getValue();
                objectBinder.convertObjectToXml(location.getClass(), fileLocation);
                
//                JsonObject locationJsonElement = new JsonObject();
//                locationJsonElement.addProperty("title", location.getTitle());
//                locationJsonElement.addProperty("coordinate", location.getCoordinate().toString());
//                locationJsonElement.addProperty("description", location.getDescription());
//                locationJsonElement.addProperty("locationType", location.getLocationType().toString());
//                locationJsonElement.addProperty("isSeen", String.valueOf(location.isSeen()));
//                locationJsonElement.addProperty("isCeatureAlive", String.valueOf(location.isCeatureAlive()));
//                locationJsonElement.addProperty("danger", String.valueOf(location.getDangerRating()));
//                jsonObject.add(location.getCoordinate().toString(), locationJsonElement);
            }
            Writer writer = new FileWriter(fileName);
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException ex) {
            Communicator.provide("Unable to save to file " + fileName);
        } catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
