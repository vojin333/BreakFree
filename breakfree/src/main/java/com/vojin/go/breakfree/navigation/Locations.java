package com.vojin.go.breakfree.navigation;

import java.util.List;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * 
 * @author Vojin Nikolic
 *
 */
@XmlRootElement(name = "locations")
@XmlAccessorType (XmlAccessType.FIELD)
public class Locations {

	@XmlElement(name = "location")
    private List<Location> locations = null;
 
    public List<Location> getLocations() {
        return locations;
    }
 
    public void setLocations(List<Location> Locations) {
        this.locations = Locations;
    }
}
