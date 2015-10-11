package com.vojin.go.breakfree.domain.repository;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.navigation.Coordinate;
import com.vojin.go.breakfree.navigation.Direction;
import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.navigation.Locations;
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
	
	/**
	 * Loads {@link Location} data for {@link Player}
	 * @throws RepositoryException
	 */
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
			throw new RepositoryException(jaxE.getMessage());
		}
	}

	/**
	 * Copies game data to player folder
	 * @throws RepositoryException
	 */
	private void copyLocationsFile() throws RepositoryException {
		try {
			File source = new File(ConfiguratioProps.LOCATION_FILE.getDescription());
			File dest = new File(fileName);
			dest.mkdirs();
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RepositoryException("Problem with setting up players data");
			
		}
	}
	
	/**
	 * 
	 * @throws RepositoryException
	 */
	public void saveLocations() throws RepositoryException {
        try {
            File fileLocation = new File(fileName);
            
            Locations locationsObject = new Locations();
            List<Location> listOfLocations = new ArrayList<Location>();
			for (Map.Entry<Coordinate, Location> entryLocation : locations.entrySet()) {
				listOfLocations.add(entryLocation.getValue());
			}
			locationsObject.setLocations(listOfLocations);
            JAXBContext context = JAXBContext.newInstance(locationsObject.getClass());
            Marshaller mar = context.createMarshaller();
            mar.marshal(locationsObject, fileLocation);
            
        } catch (JAXBException e) {
			// TODO Auto-generated catch block
        	e.printStackTrace();
        	throw new RepositoryException(e.getMessage());
			
		}
    }
	
	/**
	 * 
	 * @param loactionToSave
	 * @throws RepositoryException
	 */
	public void saveLocation(Location loactionToSave) throws RepositoryException {
		for (Map.Entry<Coordinate, Location> entryLocation : locations.entrySet()) {
			if (entryLocation.getKey().equals(loactionToSave.getCoordinate())) {
				entryLocation.setValue(loactionToSave);
			}
		}
		try {
			saveLocations();
		} catch (RepositoryException e) {
			e.printStackTrace();
			throw new RepositoryException(e.getMessage());
		}
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
