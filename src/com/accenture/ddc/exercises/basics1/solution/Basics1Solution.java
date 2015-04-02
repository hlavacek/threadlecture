package com.accenture.ddc.exercises.basics1.solution;

/*
 * Basic exercise 1
 * a) Create a thread that will generate a random integer between 0 and 10
 * b) Store this generated number in threads own member (variable) named 'int x'
 * c) Create 4 of these threads a start them in your main thread. Let main thread
 * 	  wait for all these threads to end and calculate the sum of all generated numbers.
 * d) Print the output into console from the main thread in this format: 
 * 	  'random1 + random2 + random3 + random4 = sum'
 * 
 * Hint: You can use Math.random() method to generate a random double from 0.0 to 1.0
 */

public class Basics1Solution {
	
	public static void main(String[] args) throws InterruptedException {
		//create 4 threads
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		MyThread t3 = new MyThread();
		MyThread t4 = new MyThread();
		
		//start all created threads
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		//wait for all threads to finish generating random int
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		
		//print sum
		int sum = t1.x+t2.x+t3.x+t4.x;
		System.out.print(t1.x+" + "+t2.x+" + "+t3.x+" + "+t4.x+" = "+sum);
	}
	
}

class MyThread extends Thread {
	
	int x;
	
	@Override
	public void run() {
		x = (int)(Math.random()*10); //generate random int (0-10)
	}
}
