package com.accenture.ddc.lecture.basics1;

public class ThreadCreation {
    
    public static void main(String[] args) {
        Thread myThread = new Thread(new MyThread());
        myThread.start();
    } 
}

class MyThread implements Runnable {
    
    @Override
    public void run() {
       System.out.println("This text is printed from created thread.");
    }
}
