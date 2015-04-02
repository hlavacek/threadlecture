package com.accenture.ddc.exercises.basics3.solution;

/*
 * Basic exercise 3
 * Edit receptionist to serve every customer exactly 1 time.
 * Serving a customer means printing "Customer #X has been served by receptionist." exactly
 * one time after he arrived.
 * 
 */

public class Basics3Solution {
	
	
	public static String waitingCustomer;
	public static Receptionist receptionist;
	public static ReceptionBell receptionBell;
	
	public static void main(String[] args) throws InterruptedException {
		receptionist = new Receptionist();
		receptionBell = new ReceptionBell();
		
		receptionist.start();
		receptionBell.start();
	}
}

class Receptionist extends Thread { //renamed to ReceptionistSolution because Receptionist class is already in this package
	
	public static Object lock = new Object(); //we can use any object for synchronization
	public static boolean customerIsWaiting = false; //flag we use in guarded block
	
	@Override
	public void run() {
		while(true) {
				synchronized (Receptionist.lock) { //wait must be in synchronized block
					while(customerIsWaiting == false) { //guarded block
						try {
							Receptionist.lock.wait(); //wait until notified
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
			System.out.println(Basics3Solution.waitingCustomer+" has been served by receptionist."); 
			customerIsWaiting = false; //set flag back to false after serving a customer
		}
	}
}

class ReceptionBell extends Thread {
	
	@Override
	public void run() {
		int i=0;
		while(true) {
			i++;
			ring("Customer #"+i); //new customer arrived
			synchronized (Receptionist.lock) {
				Receptionist.customerIsWaiting = true; //set guarded block flag to true
				Receptionist.lock.notify(); //notify thread waiting on lock object
			}
			try {
				Thread.sleep(1500); //pause between customer arrivals
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ring (String customerName) {
		Basics3Solution.waitingCustomer = customerName; //customer is waiting
		System.out.println(customerName+" has arrived.");
	}
}