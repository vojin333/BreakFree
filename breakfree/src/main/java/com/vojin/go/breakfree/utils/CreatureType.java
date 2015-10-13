package com.vojin.go.breakfree.utils;



/**
 * 
 * @author Vojin Nikolic
 * 
 * Enumerator for creature type
 *
 */
public enum CreatureType {

	FEMALE("A Zombie (woman) is going fast,she is almost running, she has long dark hair, white eyes, she is short and thin, her guts are falling out while she moves, but that doesn't stop her, she is making horrible sounds, mix of roaring, screaming and suffocating at the same time,"),
	MALE("A Zombie (man) is approaching - he is tall, slow, dragging his big body, making some horrible, noise his eyes are white, half of his jaw is ripped, and worms are slithering from his every bloody, flash wound of his massive body."),
	CHILD("A Zombie (child) is getting up slowly from a sitting position, approaching at normal speed, it is a small boy maybe 6 years old with blond hair and white eyes, he has a blood coming out of his mouth, he is chewing something. In one hand he is carrying a toy, Vinni the Poo, his other arm is whole out of flesh, no skin on it."),
	QUEEN("A Mutant Zombie. She is big, strong and fast. She has needle hanging from her vain. Her head is open, but she is running and from her big white eyes blood is pouring. She is opening her big hands and she is approaching very fast towards you.");
	
	private final String description;
	
	CreatureType(String desc){
		this.description = desc;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	
}
