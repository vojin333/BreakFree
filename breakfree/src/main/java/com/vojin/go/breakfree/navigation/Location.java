package com.vojin.go.breakfree.navigation;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.vojin.go.breakfree.domain.entities.Zombie;
import com.vojin.go.breakfree.utils.Communicator;

/**
 * 
 * @author Vojin Nikolic
 *
 * This class represents a map Unit
 */
@XmlRootElement(name = "location")
public class Location {
	
	private Coordinate coordinate;
	private String title;
	private String description;
	private LocationType locationType;
	private int dangerRating;
	private boolean isCeatureAlive;
	private boolean isSeen;
	private boolean isFinal;
	
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
    @XmlElement(name = "coordinate")
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
	@XmlElement(name = "title")
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
	@XmlElement(name = "description")
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
	@XmlElement(name = "locationType")
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
	@XmlElement(name = "dangerRating")
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
	@XmlTransient
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
	@XmlElement(name = "isCeatureAlive")
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
	@XmlElement(name = "isSeen")
	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	/**
	 * @return the isFinal
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * @param isFinal the isFinal to set
	 */
	@XmlElement(name = "isFinal")
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
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
		Communicator.provide("\n" + "Your current location is:");
        Communicator.provide(getTitle() + ":");
        Communicator.provide("    " + getDescription());
        Communicator.provide("\n");
    }
}
