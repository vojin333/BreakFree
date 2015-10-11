package com.vojin.go.breakfree.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * 
 * @author Vojin Nikolic
 * 
 * Class responsible for Converting Objects to XML and vice versa
 *
 */
public class ObjectBinder {
	
	/**
	 * Marshals Xml to an Object
	 * @param classTo
	 * @param location
	 * @return
	 * @throws JAXBException
	 */
	public <T> T convertXMLToObject(Class<?> classTo, File location) throws JAXBException{
        try {
        	JAXBContext context = JAXBContext.newInstance(classTo);
            Unmarshaller um = context.createUnmarshaller();
            return (T) um.unmarshal(location);
        } catch (JAXBException je) {
            throw new JAXBException("Error interpreting XML response", je);
        }
    }
	
	/**
	 * Marshals Object to an XML
	 * @param classTo
	 * @param location
	 * @throws JAXBException
	 */
	public void convertObjectToXml(Class<?> classTo, File location) throws JAXBException{
        try {
        	JAXBContext context = JAXBContext.newInstance(classTo);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(classTo, location);
        } catch (JAXBException je) {
            throw new JAXBException("Error Saving Object to  XML response", je);
        }
    }
}
