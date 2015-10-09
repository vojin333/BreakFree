package com.vojin.go.breakfree.navigation;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.vojin.go.breakfree.domain.entities.Zombie;
import com.vojin.go.breakfree.utils.Communicator;

/**
 * 
 * @author Vojin Nikolic
 *
 * This class represents a map Unit
 */
@XmlRootElement(name = "location")
@XmlAccessorType (XmlAccessType.FIELD)
public class Location {
	
	@XmlElement(name = "coordinate")
	private Coordinate coordinate;
	
	@XmlElement(name = "title")
	private String title;
	
	@XmlElement(name = "description")
	private String description;
	
	@XmlElement(name = "locationType")
	private LocationType locationType;
	
	@XmlElement(name = "dangerRating")
	private int dangerRating;
	
	@XmlElement(name = "isCeatureAlive")
	private boolean isCeatureAlive;
	
	@XmlElement(name = "isSeen")
	private boolean isSeen;
	
	
    private Zombie zombie;
	
    public Location() {

    }
    
    public Location(Coordinate coordinate, String title, String description, LocationType locationType, boolean isCreatureAlive, boolean isSeen ) {
        this.coordinate = coordinate;
        this.title = title;
        this.description = description;
        this.locationType = locationType;
        this.isCeatureAlive = isCreatureAlive;
        this.isSeen = isSeen;
    }

	/**
	 * @return the coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(Coordinate coordinateRaw) {
		this.coordinate = coordinateRaw;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the locationType
	 */
	public LocationType getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return the dangerRating
	 */
	public int getDangerRating() {
		return dangerRating;
	}

	/**
	 * @param dangerRating the dangerRating to set
	 */
	public void setDangerRating(int dangerRating) {
		this.dangerRating = dangerRating;
	}
    
	/**
	 * @return the zombie
	 */
	public Zombie getZombie() {
		return zombie;
	}

	/**
	 * @param zombie the zombie to set
	 */
	public void setZombie(Zombie monster) {
		this.zombie = monster;
	}

	/**
	 * 
	 * @return isCeatureAlive
	 */
	public boolean isCeatureAlive() {
		return isCeatureAlive;
	}
	
	/**
	 * 
	 * @param isCeatureAlive
	 */
	public void setCeatureAlive(boolean isCeatureAlive) {
		this.isCeatureAlive = isCeatureAlive;
	}

	/**
	 * 
	 * @return isSeen
	 */
	public boolean isSeen() {
		return isSeen;
	}

	/**
	 * 
	 * @param isSeen
	 */
	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [coordinate=" + coordinate + ", title=" + title + ", description=" + description + ", locationType=" + locationType + ", dangerRating=" + dangerRating + ", isCeatureAlive="
				+ isCeatureAlive + ", isSeen=" + isSeen + ", zombie=" + zombie + "]";
	}

	/**
	 * Prints current location
	 * 
	 * @see
	 */
	public void print() {
        Communicator.provide("\n" + getTitle() + ":");
        Communicator.provide("    " + getDescription());
    }
}
