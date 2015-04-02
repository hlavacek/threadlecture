/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ddc.exercises.exercise3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vladimir.hlavacek
 */
public class PrimeNumbersQueue {

//    Total lines count 9000000, prime numbers count 457796.
//    Execution took 22235 miliseconds.
    public static final String SAMPLE_FILE = "data/exercise3/sample.txt";

    private static final double HIGHEST_RANDOM_NUMBER = 1e9;
    private static final int TOTAL_NUMBER_COUNT = 9000000;
    private static final boolean FORCE_SAMPLE_FILE_GENERATION = false;

    public static void main(String[] args) {
        generateSampleFile();

        long startTime = System.currentTimeMillis();

        findPrimeNumbers();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println(String.format("Execution took %s miliseconds.", executionTime));

    }

    /**
     * This method generates a sample file with ~170 MB size
     */
    private static void generateSampleFile() {
        File sampleFile = new File(SAMPLE_FILE);
        sampleFile.getParentFile().mkdirs();

        if (!FORCE_SAMPLE_FILE_GENERATION && sampleFile.exists()) {
            // do not generate sample file if it already exists
            return;
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(sampleFile, false), "UTF-8")) {
            for (int i = 0; i < TOTAL_NUMBER_COUNT; i++) {

                int numberToWrite = (int) (HIGHEST_RANDOM_NUMBER * Math.random());

                writer.write(numberToWrite + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Sample file generated");

    }

    private static void findPrimeNumbers() {
        int numberCount = 0;
        int primeNumberCount = 0;

        BlockingQueue loadedNumbers = new LinkedBlockingQueue();
        List<PrimeNumberQueueThread> queueThreads = new ArrayList<>();

        int threadCount = 3;
        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            PrimeNumberQueueThread queueThread = new PrimeNumberQueueThread(loadedNumbers);
            queueThreads.add(queueThread);
            queueThread.start();
        }

        try (Scanner scanner = new Scanner(new File(SAMPLE_FILE), "UTF-8")) {
            while (scanner.hasNextInt()) {
                loadedNumbers.add(scanner.nextInt());
            }

        } catch (IOException ex) {
            Logger.getLogger(PrimeNumbersQueue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            for (PrimeNumberQueueThread queueThread : queueThreads) {
                queueThread.end();
                queueThread.join();
                primeNumberCount += queueThread.getPrimeNumberCount();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Prime numbers count %s.", primeNumberCount));
    }

    public static boolean isPrimeNumber(int number) {
        if (number == 1 || number == 2) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        int sqrt = (int) Math.sqrt(number);

        for (int i = 3; i <= sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;

    }

}

class PrimeNumberQueueThread extends Thread {

    private int primeNumberCount;
    private final BlockingQueue<Integer> loadedNumbers;
    private volatile boolean stopped = false;

    PrimeNumberQueueThread(BlockingQueue<Integer> loadedNumbers) {
        this.loadedNumbers = loadedNumbers;
    }

    public int getPrimeNumberCount() {
        return this.primeNumberCount;
    }

    public void end() {
        this.stopped = true;
    }

    @Override
    public void run() {
        Integer nextNumber;
        try {
            while (!stopped) {

                while ((nextNumber = loadedNumbers.poll(200, TimeUnit.MILLISECONDS)) != null)  {

                    if (PrimeNumbersQueue.isPrimeNumber(nextNumber)) {
                        this.primeNumberCount = this.primeNumberCount + 1;
                    }
                }
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(PrimeNumberQueueThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
