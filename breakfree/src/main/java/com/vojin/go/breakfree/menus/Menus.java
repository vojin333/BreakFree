package com.vojin.go.breakfree.menus;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vojin.go.breakfree.BreakFree;
import com.vojin.go.breakfree.utils.Communicator;

/**
 * 
 * @author Vojin Nikolic
 *
 * All menus in {@link BreakFree} game extend this class
 */
public class Menus {
    protected List<MenuItem> menuItems = new ArrayList<>();
    protected Map<String, MenuItem> commandMap = new HashMap<String, MenuItem>();

    /**
     * 
     * @param menuOption
     * @return
     * @see
     */
	public MenuItem displayMenu(List<MenuItem> menuOption) {
		int i = 1;
		for (MenuItem menuItem : menuOption) {
			commandMap.put(String.valueOf(i), menuItem);
			commandMap.put(menuItem.getInstructionCommand(), menuItem);
			for (String command : menuItem.getAltCommands()) {
				commandMap.put(command.toLowerCase(), menuItem);
			}
			i++;
		}
		MenuItem selectedItem = selectMenu(menuOption);
		return selectedItem;
	}


	/**
	 * Selects menu item
	 * @param menuOption
	 * @return
	 * @see
	 */
    
	protected MenuItem selectMenu(List<MenuItem> menuOption) {
        this.printMenuItems(menuOption);
        String command = Communicator.accept();
        if (commandMap.containsKey(command.toLowerCase())) {
            return commandMap.get(command.toLowerCase());
        } else {
            Communicator.provide("I don't know what '" + command + "' means.");
            return this.displayMenu(menuOption);
        }
    }

	/**
	 * Prints all menu items
	 * @param menuOption
	 */
    private void printMenuItems(List<MenuItem> menuOption) {
        int i = 1;
        for (MenuItem menuItem: menuOption) {
            if (menuItem.getDescription() != null) {
                Communicator.provide("[" + i + "] " + menuItem.getCommand() + " - " + menuItem.getDescription());
            } else {
                Communicator.provide("[" + i + "] " + menuItem.getCommand());
            }
            i++;
        }
    }
}

