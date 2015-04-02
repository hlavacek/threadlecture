package com.accenture.ddc.exercises.basics5.solution;

public class DeadLock2Solution {
	
	private final BankAccount acc1 = new BankAccount(1000,1); //add id 1 to our first BankAccount object
	private final BankAccount acc2 = new BankAccount(2000,2); //add id 2 to our second BankAccount object
	
	public static void main(String[] args) throws InterruptedException {
		while(true) {
		new DeadLock2Solution().go();}
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
		BankAccount first = from;
		BankAccount second = to;
		if(first.id < second.id) { //sort "lock objects" by id
			first = to;
			second = from;
		}
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
	int id; //added unique identifier for BankAccount
	
	public BankAccount(int openingAmount, int id) { 
		this.money = openingAmount;
		this.id = id;
	}
	
}

