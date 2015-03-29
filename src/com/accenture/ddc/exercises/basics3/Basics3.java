package com.accenture.ddc.exercises.basics3;

/*
 * Basic exercise 3
 * Edit receptionist to serve every customer exactly 1 time.
 * Serving a customer means printing "Customer #X has been served by receptionist." exactly
 * one time after he arrived.
 * 
 */

public class Basics3 {
	
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

class Receptionist extends Thread {
	@Override
	public void run() {
		while(true) {
			//TODO: serve every customer ONLY ONCE!
			System.out.println(Basics3.waitingCustomer+" has been served by receptionist.");
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
			
			try {
				Thread.sleep(1500); //pause between customer arrivals
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ring (String customerName) {
		Basics3.waitingCustomer = customerName; //customer is waiting
		System.out.println(customerName+" has arrived.");
	}
}