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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vladimir.hlavacek
 */
public class PrimeNumbersIndexSeparation {
    
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
        
        List<Integer> loadedNumbers = loadNumbers();
        
        int threadCount = 10;
        List<PrimeNumberThread> primeNumberThreads = new ArrayList<>();
        int numbersToProcessForOneThread = (loadedNumbers.size() / threadCount);
        for (int threadIndex = 0; threadIndex < threadCount; threadIndex ++) {
            int startIndex = threadIndex * numbersToProcessForOneThread;
            
            PrimeNumberThread primeNumberThread = new PrimeNumberThread(loadedNumbers, startIndex, startIndex + numbersToProcessForOneThread);
            primeNumberThreads.add(primeNumberThread);
            
            primeNumberThread.start();
        }
        
        try {
        for (PrimeNumberThread primeNumberThread : primeNumberThreads) {
            primeNumberThread.join();
            primeNumberCount += primeNumberThread.getPrimeNumberCount();
        }

        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println(String.format("Total lines count %s, prime numbers count %s.", numberCount, primeNumberCount));
    }

    private static List<Integer> loadNumbers() {
        List<Integer> loadedNumbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(SAMPLE_FILE), "UTF-8")) {
            while (scanner.hasNextInt()) {
                loadedNumbers.add(scanner.nextInt());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(PrimeNumbersIndexSeparation.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loaded input file.");
        return loadedNumbers;
    }

    public static boolean isPrimeNumber(int number) {
        if (number == 1 || number == 2) {
            return true;
        }
        
        if (number % 2  == 0) {
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

class PrimeNumberThread extends Thread {
    private final List<Integer> numbersToProcess;
    private final int startIndex;
    private final int endIndex;
    
    private int primeNumberCount = 0;

    public PrimeNumberThread(List<Integer> numbersToProcess, int startIndex, int endIndex) {
        this.numbersToProcess = numbersToProcess;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getPrimeNumberCount() {
        return primeNumberCount;
    }
    
    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            
            int numberToCheck = numbersToProcess.get(i);
            if (PrimeNumbersIndexSeparation.isPrimeNumber(numberToCheck)) {
                this.primeNumberCount++;
            }
        }
        
        System.out.println(String.format("Thread processing from %s to %s is stopped.", startIndex, endIndex));
        
    }
    
    
}

