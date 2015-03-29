package com.accenture.ddc.exercises.basics4;

/*
 * This code is deadlock prone.
 * Edit this code so it will never go into deadlock.
 */

public class DeadLock1 {
	
	public static Thread1 thread1;
	public static Thread2 thread2;
	public static int safeVariable = 0;
	
	public static void main(String[] args) throws InterruptedException {
			thread1 = new Thread1();
			thread2 = new Thread2();
			thread1.start();
			thread2.start();
			
			thread1.join(); //wait for thread1 to finish
			thread2.join(); //wait for thread2 to finish
			System.out.println("End.");
	}
	
}

class Thread1 extends Thread {

	@Override
	public void run() {
		synchronized (DeadLock1.thread2) {
			synchronized (DeadLock1.thread1) {
				DeadLock1.safeVariable++;
				System.out.println(DeadLock1.safeVariable);
			}
		}
	}
}

class Thread2 extends Thread {

	@Override
	public void run() {
		synchronized (DeadLock1.thread1) { 
			synchronized (DeadLock1.thread2) {
				DeadLock1.safeVariable++;
				System.out.println(DeadLock1.safeVariable);
			}
		}
	}
}
