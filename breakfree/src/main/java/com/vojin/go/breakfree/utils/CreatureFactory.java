package com.vojin.go.breakfree.utils;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.vojin.go.breakfree.domain.entities.Zombie;

/**
 * 
 * @author Vojin Nikolic
 *
 * This class creates game creatures randomly
 *
 */
public class CreatureFactory {

	private final static Random random = new Random();
	private final static int NUM_ZOMBIES = 3;
	private final static Set<Integer> monstersSeen = new HashSet<Integer>();
	
	public static Zombie createCreature() {
		 if (monstersSeen.size() == NUM_ZOMBIES) {
	            monstersSeen.clear();
	        }
	        int i;
	        do {
	            i = random.nextInt(NUM_ZOMBIES);
	        } while (monstersSeen.contains(i));
	        monstersSeen.add(i);
        if (i == 0) {
            return new Zombie("Male", CreatureType.MALE.getDescription(), 30, 8, 12);
        } else if (i == 1) {
            return new Zombie("Feamle", CreatureType.FEMALE.getDescription(), 25, 6, 8);
        } else {
            return new Zombie("Child", CreatureType.CHILD.getDescription(), 20, 4, 6);
        }
    }
}
