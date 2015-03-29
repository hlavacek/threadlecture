package com.accenture.ddc.lecture.basics3;

public class ThreadSleep {
    
    public static void main(String[] args) {
        Thread myThread = new Thread(new MyThread());
        myThread.setName("SleepyThread");
        myThread.start();
    }
}

class MyThread implements Runnable {
    
    @Override
    public void run() {
        int sleepTime = 5000;
        System.out.println("Sleeping for "+sleepTime+" miliseconds.");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {}
        System.out.println("I am awake.");
    }
}
