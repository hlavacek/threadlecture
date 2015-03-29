package com.accenture.ddc.lecture.basics4;

public class ThreadJoin {
    
    public static void main(String[] args) {
        Thread myThread = new Thread(new MyThread());
        myThread.setName("ThreadSlowAtMath");
        myThread.start();
        
        try {
            myThread.join();
        } catch (InterruptedException ex) {}
        
        System.out.println(MyThread.x); //what will be printed if we remove join?
    }
}

class MyThread implements Runnable {
    
    public static int x;
    
    @Override
    public void run() {
        try {
            Thread.sleep(5000); //sleep for few seconds
        } catch (InterruptedException ex) {}
        x = 5 + 5;
    }
    
}