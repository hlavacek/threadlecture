package com.accenture.ddc.exercises.basics2.solution;

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

public class Basics2Solution {
	
	protected static int[] myArray = new int[4];
	
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
		int sum = myArray[0]+myArray[1]+myArray[2]+myArray[3];
		System.out.print(myArray[0]+" + "+myArray[1]+" + "+myArray[2]+" + "+myArray[3]+" = "+sum);
	}
	
}

class MyThread extends Thread {
	
	static int index = 0;
	
	@Override
	public void run() {
		int x = (int)(Math.random()*10); //generate random int (0-10)
		synchronized (Basics2Solution.myArray) { //critical section
			Basics2Solution.myArray[index] = x; //add random to number to array
			index++; //raise index by 1 for next thread
		}
	}
}
