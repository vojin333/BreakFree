package com.vojin.go.breakfree.utils;



/**
 * 
 * @author Vojin Nikolic
 *
 * Exception that is thrown upon death of a player
 */
public class GameOverException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public GameOverException(String message) {
		super();
		this.message = message;
	}

	public String getGameMessage() {
		return this.message;
	}
}
