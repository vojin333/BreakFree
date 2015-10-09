package com.vojin.go.breakfree.menus;





import java.util.ArrayList;
import java.util.List;

import com.vojin.go.breakfree.GameWorld;
import com.vojin.go.breakfree.dependency.BeanFactory;
import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.GameOverException;
import com.vojin.go.breakfree.utils.RepositoryException;

/**
 * 
 * @author Vojin Nikolic
 * Character creation menu
 *
 */
public class CreateCharacterMenu extends Menus {

	private List<MenuItem> characterSexMenuItems = new ArrayList<>(); 

	public CreateCharacterMenu(String playerName) throws GameOverException, RepositoryException{
		this.characterSexMenuItems.add(new MenuItem("male", "male has initialy more stronger hits but slower experiance gain"));
		this.characterSexMenuItems.add(new MenuItem("female", "female has initialy less stronger hits but faster experiance gain"));
		
		while (true) {
			Communicator.provide("Choose sex of your character");
			MenuItem selectedMenuItem = displayMenu(characterSexMenuItems);
			if(selectAnItem(selectedMenuItem, playerName)){
				break;
			}
			
		}
	}
	
	private boolean selectAnItem(MenuItem menuItem, String playerName) throws GameOverException, RepositoryException{
		String item = menuItem.getInstructionCommand();
		if (item.equals("male")) {
			Player newPlayer = new Player();
			newPlayer.setName(playerName);
			newPlayer.setLocationRepo(BeanFactory.createLocationRepository(playerName));
			//setIntial coordinate
			newPlayer.setCurrentLocation(newPlayer.getLocationRepo().getInitialLocation());
			newPlayer.setSex(item);//TODO set health per sex + set num potions
			newPlayer.savePlayer();
			new GameWorld(newPlayer, "new");
			return true;
		} else if (item.equals("female")) {
			Player newPlayer = new Player();
			newPlayer.setName(playerName);
			newPlayer.setLocationRepo(BeanFactory.createLocationRepository(playerName));
			//setIntial coordinate
			newPlayer.setCurrentLocation(newPlayer.getLocationRepo().getInitialLocation());
			newPlayer.setSex(item);//TODO set health per sex + set num potions
			newPlayer.savePlayer();
			new GameWorld(newPlayer, "new");
			return true;
		} else {
			return false;
		}

	}
}
