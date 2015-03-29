package com.accenture.ddc.exercises.basics5;

/*
 * This code is deadlock prone.
 * Edit this code so it will never go into deadlock.
 */

public class DeadLock2 {
	
	private final BankAccount acc1 = new BankAccount(1000);
	private final BankAccount acc2 = new BankAccount(2000);
	
	public static void main(String[] args) throws InterruptedException {
		new DeadLock2().go();
	}
	
	public void go() throws InterruptedException {
		Thread thread1 = new Thread() {
			public void run() {
				transferMoney(acc1, acc2, 1000);
			};
		};
		
		Thread thread2 = new Thread() {
			public void run() {
				transferMoney(acc2, acc1, 500);
			};
		};
		
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		System.out.println("Amount of $ on acc1: "+acc1.money);
		System.out.println("Amount of $ on acc1: "+acc2.money);
	}
	
	public void transferMoney(BankAccount from, BankAccount to, int amount) {
		synchronized (from) {
			synchronized (to) {
				from.money = from.money - amount;
				to.money = to.money + amount;
			}
		}
	}
}

class BankAccount {
	
	int money;
	
	public BankAccount(int openingAmount) {
		this.money = openingAmount;
	}
	
}

