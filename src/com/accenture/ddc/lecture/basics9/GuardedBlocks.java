package com.accenture.ddc.lecture.basics9;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vladimir.hlavacek
 */
public class GuardedBlocks {

    public static void main(String[] args) {

        MessageHolder messageHolder = new MessageHolder();
        
        MessageProducer messageProducer = new MessageProducer(messageHolder);
        Thread messageProducerThread = new Thread(messageProducer);
        
        MessageConsumer messageConsumer = new MessageConsumer(messageHolder);
        Thread messageConsumerThread = new Thread(messageConsumer); 
        
        messageConsumerThread.start();
        
        try {
            Thread.sleep(300l);
            
            messageProducerThread.start();
            Thread.sleep(1000l);
            
            messageProducer.stop();
            messageConsumer.stop();
            
            messageProducerThread.join();
            messageConsumerThread.join();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(GuardedBlocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class MessageHolder {

    private String message;

    public synchronized String getMessage() {
        String messageCopy = message;
        this.message = null;
        return messageCopy;
    }

    public synchronized void setMessage(String message) {
        this.message = message;
        notify();
    }
}

class MessageProducer implements Runnable {

    private final MessageHolder messageHolder;

    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;
    }

    public MessageProducer(MessageHolder messageHolder) {
        this.messageHolder = messageHolder;
    }

    @Override
    public void run() {

        int messageCount = 0;
        while (!stopped) {
            try {
                // simulate some work
                Thread.sleep(100l);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            messageCount++;
            String message = "My message " + messageCount;
            System.out.println("Sending message: " + message);
            messageHolder.setMessage(message);
        }
    }

}

class MessageConsumer implements Runnable {

    private final MessageHolder messageHolder;

    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;
    }

    public MessageConsumer(MessageHolder messageHolder) {
        this.messageHolder = messageHolder;
    }

    @Override
    public void run() {

        while (!stopped) {
            synchronized (messageHolder) {
                try {
                    //messageHolder.wait(100l);

                    String message = messageHolder.getMessage();
                    
                    // try to receive the current message
                    if (message != null) {
                        System.out.println("Received message : " + message);
                    } else {
                        System.out.println("There was no message to receive.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

}
