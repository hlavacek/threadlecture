package com.accenture.ddc.lecture.basics8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author vladimir.hlavacek
 */
public class StoppingThreads {

    public static final String SAMPLE_FILE = "data/basics8/sample.txt";

    public static void main(String[] args) {
        //generateSampleFile();

        CountThread countThreadObject = new CountThread();
        Thread countThread = new Thread(countThreadObject);
        countThread.start();

        try {
            Thread.sleep(1000l);

            countThreadObject.stop();

            countThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method generates a sample file with ~170 MB size
     */
    private static void generateSampleFile() {
        File sampleFile = new File(SAMPLE_FILE);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(sampleFile, false), "UTF-8")) {
            for (int i = 0; i < 9000000; i++) {
                writer.write(Double.toString(Math.random()) + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}

class CountThread implements Runnable {

    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;
    }

    @Override
    public void run() {

        double totalCount = 0;
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(StoppingThreads.SAMPLE_FILE), "UTF-8"))) {
            String line = null;

            while (!stopped && (line = reader.readLine()) != null) {
                double currentLineDouble = Double.parseDouble(line);
                totalCount += currentLineDouble;
                lineCount++;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println(String.format("Processed %s lines, total sum is %s.", lineCount, totalCount));

    }
}
