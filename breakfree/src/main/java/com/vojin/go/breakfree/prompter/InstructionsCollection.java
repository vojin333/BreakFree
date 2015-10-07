package com.vojin.go.breakfree.prompter;



import java.util.HashMap;
import java.util.Map;

import com.vojin.go.breakfree.Battle;
import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.navigation.Direction;
import com.vojin.go.breakfree.navigation.Location;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.GameOverException;

/**
 * 
 * @author Vojin Nikolic
 *
 * CommandCollection contains the declaration of the methods mapped to game commands
 *
 * The declared command methods are accessed only by {@link InstructionHandler}.
 * Add an appropriate method to this class and Annotate it with {@link Instruction}
 *
 */
public enum InstructionsCollection {
    INSTANCE;

    public Player player;

    private final static Map<String, String> MOVE_DIRECTIONS = new HashMap<String,String>();
    static {
        MOVE_DIRECTIONS.put("n", "north");
        MOVE_DIRECTIONS.put("s", "south");
        MOVE_DIRECTIONS.put("e", "east");
        MOVE_DIRECTIONS.put("w", "west");
    }

    public static InstructionsCollection getInstance() {
        return INSTANCE;
    }

    public void initPlayer(Player player) {
        this.player = player;
    }

    //TODO help instruction to print all possible instructions

    @Instruction(instruction="fight", aliases="fight", description="Battle Starts")
	public void commandFighting() throws GameOverException {
		if (player.getCurrentLocation().getZombie().isAlive()) {
			new Battle(player, player.getCurrentLocation().getZombie());
		}
	}
    
    @Instruction(instruction="attack", aliases="a", description="Attacks")
	public void commandAttackCreature() {
    	player.getCurrentLocation().getZombie().defend(player);
	}
    
    @Instruction(instruction="heal", aliases="h", description="Healing")
	public void commandHealing() {
    	if (player.getNumPotions() > 0) {
			player.heal();
		} else {
			Communicator.provide(" You've exhausted your potion supply!");
		}
	}
    
    @Instruction(instruction="go", aliases="g", description="Goto a direction")
	public void commandGo(String arg) {
		
		arg = MOVE_DIRECTIONS.get(arg);
		
		Direction direction = Direction.valueOf(arg.toUpperCase());
		Map<Direction, Location> exits = player.getLocationRepo().getPossibleExits(player.getCurrentLocation().getCoordinate());
		if (exits.containsKey(direction)) {
			Location newLocation = exits.get(Direction.valueOf(arg.toUpperCase()));
			player.setCurrentLocation(newLocation);
			player.getCurrentLocation().setSeen(true);
			player.getLocationRepo().saveLocation(player.getCurrentLocation());
			Communicator.provide("** The Check Point has been saved **");
			player.getCurrentLocation().print();	
			//TODO keep a track of the monsters
		} else {
			Communicator.provide("There is no exit that way.");
		}
	}
   
    @Instruction(instruction="save", aliases="s", description="Save")
	public void commandSaving() {
    	player.savePlayer();
		
	}
    	
 
}
