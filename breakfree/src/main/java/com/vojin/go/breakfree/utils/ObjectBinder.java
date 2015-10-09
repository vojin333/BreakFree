package com.vojin.go.breakfree.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.navigation.Locations;

public class ObjectBinder {

	
	public void loadObjectFromXML() throws JAXBException{
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Locations.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Locations locations = (Locations) jaxbUnmarshaller.unmarshal(new File("C:\\file.xml")); 
			
			List<Location> locationns2 = new ArrayList<Location>();
			locationns2 = locations.getLocations();
			for (Location location2 : locationns2) {
				location2.print();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	} 
	
	
	
	
	public <T> T convertXMLToObject(Class<?> classTo, File location) throws JAXBException{
        try {
        	JAXBContext context = JAXBContext.newInstance(classTo);
            Unmarshaller um = context.createUnmarshaller();
            return (T) um.unmarshal(location);
        } catch (JAXBException je) {
        	je.printStackTrace();
            throw new JAXBException("Error interpreting XML response", je);
        }
    }
	
	
	public <T> void convertObjectToXml(Class<?> classTo, File location) throws JAXBException{
        try {
        	JAXBContext context = JAXBContext.newInstance(classTo);
            Marshaller um = context.createMarshaller();
            um.marshal(classTo, location);
        } catch (JAXBException je) {
        	je.printStackTrace();
            throw new JAXBException("Error Saving Object to  XML response", je);
        }
    }
	
}
