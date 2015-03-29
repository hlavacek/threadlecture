package com.accenture.ddc.lecture.basics2;

public class ThreadName {
    
    public static void main(String[] args) {
        Thread myThread = new Thread(new MyThread());
        myThread.setName("FunnyThread");
        myThread.start();
    }
}

class MyThread implements Runnable {
    
    @Override
    public void run() {
       System.out.println("This text is printed by "+Thread.currentThread().getName());
    }
}
