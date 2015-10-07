package com.vojin.go.breakfree.utils;



import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * @author Vojin Nikolic
 *
 * This class is responsible for providing and accepting all commands 
 *
 */
public class Communicator {

	private static Scanner input = null;
	
	public static void provide(String message) {
		System.out.println(message);
	}

	public static String accept() {
		String message = null;
		try {
			input = new Scanner(System.in);
			message = input.nextLine();
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
		}

		return message;
	}

}
