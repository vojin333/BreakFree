package com.vojin.go.breakfree.utils;

/**
 * 
 * @author Vojin Nikolic
 *
 */
public class RepositoryException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public RepositoryException(String message) {
		super();
		this.message = message;
	}

	public String getRepositoryMessage() {
		return this.message;
	}
}
