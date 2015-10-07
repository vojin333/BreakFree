package com.vojin.go.breakfree.utils;



/**
 * 
 * @author Vojin Nikolic
 * 
 * Configuration property file
 *
 */
public enum ConfiguratioProps {
	LOCATION_FOLDER(System.getProperty("user.home") + "/breakfree/game_data/game_world_data/"),
	LOCATION_FILE(System.getProperty("user.home") + "/breakfree/game_data/game_world_data/locations.json"),
	PLAYER_LOCATION(System.getProperty("user.home") + "/breakfree/game_data/user_data/");

	private String description;
	ConfiguratioProps (String desc) {
		this.description = desc;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
