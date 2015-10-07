package com.vojin.go.breakfree.dependency;



import com.vojin.go.breakfree.domain.repository.LocationRepository;

/**
 * 
 * @author Vojin Nikolic
 * 
 * Class that deals with Dependency Injection
 *
 */
public final class BeanFactory {

	public static LocationRepository createLocationRepository(String playerName) {
		return new LocationRepository(playerName);
	}
	
}
