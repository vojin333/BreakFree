package com.vojin.go.breakfree.prompter;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

import com.vojin.go.breakfree.domain.entities.Player;
import com.vojin.go.breakfree.utils.Communicator;
import com.vojin.go.breakfree.utils.GameOverException;


/**
 * 
 * @author Vojin Nikolic
 *
 * InstructionHandler parses all the game commands inputs.
 * To extend method set add method to {@link InstructionsCollection}
 */
public class InstructionHandler {
    Player player;
    private TreeMap<String, Method> instructionMap;

    public InstructionHandler(Player player){
        this.player = player;
        instructionMap = new TreeMap<String, Method>();
        initinstructionMap();
    }

    // adds the command to the commandMap
	private void initinstructionMap() {
		Method[] methods = InstructionsCollection.class.getMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(Instruction.class)) {
				continue;
			}
			Instruction annotation = method.getAnnotation(Instruction.class);
			this.instructionMap.put(annotation.instruction(), method);
			for (String alias : annotation.aliases().split(",")) {
				if (alias.length() == 0) {
					break;
				}
				this.instructionMap.put(alias, method);
			}
		}
	}

	public boolean parse(Player player, String instruction) throws GameOverException {
		InstructionsCollection instructionCollection = InstructionsCollection.getInstance();
		instructionCollection.initPlayer(player);
		// if 'exit' prompts game will exit main loop
		if (instruction.equals("exit")) {
			return false;
		}
		for (String key : instructionMap.descendingKeySet()) {
			if (instruction.startsWith(key)) {
				Method method = instructionMap.get(key);
				if (method.getParameterTypes().length == 0) {
					if (instruction.equals(key)) {
						try {
							method.invoke(instructionCollection);
						} catch (IllegalAccessException | InvocationTargetException e) {
							if (e.getCause() instanceof GameOverException) {
								throw (GameOverException) e.getCause();
							} else {
								e.getCause().printStackTrace();
							}
						}
					} else {
						Communicator.provide("Instruction '" + instruction + "' is not recognized.");
						return true;
					}
				} else if (method.getParameterTypes()[0] == String.class) {
					String arg = instruction.substring(key.length()).trim();
					try {
						method.invoke(instructionCollection, arg);
					} catch (IllegalAccessException | InvocationTargetException e) {
						if (e.getCause() instanceof GameOverException) {
							throw (GameOverException) e.getCause();
						} else {
							e.getCause().printStackTrace();
						}
					}
				}
				return true;
			}
		}
		Communicator.provide("Instruction '" + instruction + "' is not recognized.");
		return true;
	}
}
