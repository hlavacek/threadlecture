package com.accenture.ddc.lecture.basics5;

public class Atomicity {
    
    public static void main(String[] args) throws InterruptedException {
         for(int i=0; i<5; i++) {
            new Atomicity().go();
         }
    }
    
    void go() throws InterruptedException {
        MyThread.x = 0;
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        Thread thread3 = new Thread(new MyThread());
        
        //start all threads
        thread1.start();
        thread2.start();
        thread3.start();
        
        //wait for all theads to finish
        thread1.join();
        thread2.join();
        thread3.join();
        
        System.out.println(MyThread.x);
    }
}

class MyThread implements Runnable {
    
    public static int x;
    
    @Override
    public void run() {
        for(int i=0; i<1000; i++) {
           x++;
        }
    }
}