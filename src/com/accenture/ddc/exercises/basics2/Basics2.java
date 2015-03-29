package com.accenture.ddc.exercises.basics2;

/*
 * Basic exercise 2
 * 
 * Reuse and edit your complete solution from 'Basic exercise 1'
 * a) Instead of saving the random generated number into the threads own variable 'int x',
 * 	  save this number directly into the shared integer array named 'myArray'
 * b) Like in exercise 1, print the output into console from the main thread in this format: 
 * 	  'random1 + random2 + random3 + random4 = sum'
 * 
 * Hint: Use myArray carefully. It will be shared between multiple threads!
 */

public class Basics2 {
	
	protected static int[] myArray = new int[4];
	
	public static void main(String[] args) {
		
	}
	
}
