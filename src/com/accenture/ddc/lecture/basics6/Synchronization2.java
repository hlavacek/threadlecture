package com.accenture.ddc.lecture.basics6;

public class Synchronization2 {
    
    public static void main(String[] args) throws InterruptedException {
         for(int i=0; i<5; i++) {
            new Synchronization().go();
         }
    }
    
    void go() throws InterruptedException {
        MyThread2.x = 0;
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        
        //start both threads
        thread1.start();
        thread2.start();
        
        //wait for both to finish
        thread1.join();
        thread2.join();
        
        System.out.println(MyThread2.x);
    }
}

class MyThread2 implements Runnable {
    
    public static int x;
     
    @Override
    public void run() {
        for(int i=0; i<10000; i++) {
            x = increment(x);
        }  
    }
    
    private synchronized int increment(int x) {
        return x+1;
    }
}