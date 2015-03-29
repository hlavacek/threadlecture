package com.accenture.ddc.lecture.basics6;

public class Synchronization {
    
    public static void main(String[] args) throws InterruptedException {
         for(int i=0; i<5; i++) {
            new Synchronization().go();
         }
    }
    
    void go() throws InterruptedException {
        MyThread.x = 0;
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        
        //start both threads
        thread1.start();
        thread2.start();
        
        //wait for both to finish
        thread1.join();
        thread2.join();
        
        System.out.println(MyThread.x);
    }
}

class MyThread implements Runnable {
    
    public static int x;
     
    @Override
    public void run() {
        for(int i=0; i<10000; i++) {
            synchronized(MyThread.class) {
                x++; //critical section
            }
        }  
    }
}