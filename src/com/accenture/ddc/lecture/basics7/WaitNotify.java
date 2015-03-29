package com.accenture.ddc.lecture.basics7;

public class WaitNotify {

    public static Thread myThread;
    
    public static void main(String[] args) throws InterruptedException {
        myThread = new Thread(new MyThread());
        myThread.setName("SleepyThread");
        myThread.start();
        
        synchronized(WaitNotify.myThread) {
            myThread.wait();
        }
        System.out.println("50% finished");
        
        myThread.join();
        System.out.println("100% finished");
        
    }
    
}

class MyThread implements Runnable {
    
    public static int countdown = 100;
    
    @Override
    public void run() {
        while(countdown != 0) {
            countdown--;
            try {
                Thread.sleep(50); //simulate work
            } catch (InterruptedException ex) {}
            
            if(countdown == 50) { //50% of iterations finished
                synchronized(WaitNotify.myThread) {
                    WaitNotify.myThread.notify();
                }
            }
        }
        
    }
}
