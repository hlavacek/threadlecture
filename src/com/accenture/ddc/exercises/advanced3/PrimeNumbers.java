package com.accenture.ddc.exercises.advanced3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * In this exercise you can try different ways how to distribute a long taking
 * task. The exercise generates a large file with random integer numbers from 0
 * to 1 billion. The task is to count the number of prime numbers in the
 * generated file - as fast as possible, with minimum CPU time wasted. </p>
 *
 * <p>
 * Do the following steps to begin with the exercise:</p>
 * <ol>
 * <li>Launch this file e.g. via Run -> Run File (Shift + F6)</li>
 * <li>The application will generate a data file with random numbers. This file
 * is generated just once, it will not be regenerated once it exists.</li>
 * <li>After it is generated, it will go through all the files in a single
 * thread and test each number whether it is a prime number</li>
 * <li>It will count all the prime numbers in the file and print out the total
 * count.</li>
 * <li>There is also a simple built in performance monitoring, which will print
 * out how much time the counting took</li>
 * <li>Please inspect the code thoroughly to get familiar with it.</li>
 * </ol>
 *
 * <p>
 * If you have the code running, you can continue to make the counting of prime
 * numbers to go faster. These are the suggested things to try:</p>
 * <ol>
 * <li>Separate loading of the file from counting of prime numbers - load the
 * numbers into an ArrayList, and then go through the list and count prime
 * numbers inside</li>
 * <li>Create a separate thread which will receive the list from previous step
 * and count the prime numbers inside</li>
 * <li>Make the thread to receive a start and end index, saying which numbers
 * from the list it should process and count together. The thread will also need
 * to store the count in a variable accessible via getter, which the main
 * application thread should take in the end and print out</li>
 * <li>Make more than one instances of the counting threads - e.g. have 3
 * threads, each processing 30k of prime numbers.</li>
 * <li>Make the number of counting threads configurable - create a list of
 * threads ("pool"), wait for them to finish, and sum their results
 * together</li>
 * <li>You can play around with to find the best number of threads to get the
 * results fastest back. The number depends on how many CPUs you have, at some
 * point it doesn't make sense to increase number of threads for
 * processing.</li>
 * </ol>
 *
 * <p>
 * There is also more effective way - the previous approach still wasted time
 * during loading of the file, when the prime numbers were not counted. Better
 * approach is to load the numbers and test for prime numbers directly beside
 * loading new numbers. As help, use the #{link
 * java.util.concurrent.BlockingQueue} class. This is a queue implementation,
 * which can be used with multiple threads. What you need to develop is the
 * following:
 * <p>
 * <ul>
 * <li>One thread, which will load the numbers from the file and put them into a
 * BlockingQueue instance</li>
 * <li>Second thread, which will get the numbers from the BlockingQueue, test
 * them for prime numbers and count their number</li>
 * </ul>
 * <p>
 * To implement this approach, please read carefully the BlockingQueue
 * documentation, to find out which methods to use, or what is a simple way to
 * stop the prime number counting thread - you can for example use -1 as poison
 * value, at which the thread will stop when received</p>
 * <p>
 * When implemented you can play around with number of threads testing for prime
 * numbers. You will probably find out, that you need much less threads than in
 * the first suggested approach, and as you do not waste time when the numbers
 * are loaded, the complete counting is much faster.</p>
 *
 * <p>
 * You may also find a different ways how to count the files, we are not saying
 * that the suggested ones are the best ones :). The idea of the exercise is to
 * show, that even a long taking task, which cannot be mathematically optimized
 * (like finding prime numbers) can be distributed - and computer power today is
 * very cheap :)
 * </p>
 *
 * @author vladimir.hlavacek
 */
public class PrimeNumbers {

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
        int percentProcessed = 0;

        try (Scanner scanner = new Scanner(new File(SAMPLE_FILE), "UTF-8")) {
            while (scanner.hasNextInt()) {
                if (numberCount % (TOTAL_NUMBER_COUNT / 10) == 0) {
                    System.out.println(percentProcessed + "%");
                    percentProcessed += 10;
                }
                int number = scanner.nextInt();

                numberCount++;

                if (isPrimeNumber(number)) {
                    primeNumberCount++;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(PrimeNumbers.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(String.format("Total lines count %s, prime numbers count %s.", numberCount, primeNumberCount));
    }

    private static boolean isPrimeNumber(int number) {
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
