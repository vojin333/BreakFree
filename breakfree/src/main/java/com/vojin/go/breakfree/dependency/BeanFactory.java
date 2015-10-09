package com.vojin.go.breakfree.dependency;



import com.vojin.go.breakfree.domain.repository.LocationRepository;
import com.vojin.go.breakfree.utils.RepositoryException;

/**
 * 
 * @author Vojin Nikolic
 * 
 * Class that deals with Dependency Injection
 *
 */
public final class BeanFactory {

	public static LocationRepository createLocationRepository(String playerName) throws RepositoryException{
		LocationRepository locationRepository = null;
		try {
			locationRepository = new LocationRepository(playerName);
		} catch (RepositoryException e) {
			throw e;
		}
		return locationRepository;
	}
	
}
