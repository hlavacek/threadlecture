package com.accenture.ddc.exercises.advanced1.solution;

import java.awt.Color;

import com.accenture.ddc.exercises.advanced1.Animation;
import com.accenture.ddc.exercises.advanced1.Window;

/*
Exercise 1 - add your code into main method (between commented lines)
           - draw the animation (gif) consiting from series of images
           - you can find images in (data/exercise1 folder)
           - images have index from 0 to 'Images.numberOfImages()'
           - draw the images in correct order
           - draw the animation in loop
           - draw the animation in multiple threads and synchronized them correctly
           
You don't need to study the code in other classes if you don't want to.
Use this methods:
  Animation.getAnimationWidth();
    - returns -> animation width (number of pixels)

  Animation.getAnimationHeight();
    - returns -> animation height (number of pixels)

  Animation.numberOfImages();
    - returns -> number of images in animation

  Animation.pixelColorAt(int image_index, int coord_x, int coord_y);
    - int image_index -> index of specific image in animation (index can be value from 0 to 'Animation.numberOfImages() - 1'
    - int coord_x -> x coordinate of specific image
    - int coord_y -> y coordinate of specific image
    - returns -> color of pixel from image on coordinates (x,y)

  window.setPixel(int coord_x, int coord_y, Color color);
    - int coord_x -> x coordinate of window
    - int coord_y -> y coordinate of window
    - Color color -> color to be set on coordinates (x,y) of window
    - ! this method will not repaint (update) the window !

  window.repaintCanvas();
    - call this method to repaint the window after pixel colors are set
*/

public class Excercise1Solution {
	
	
	
    
    public static void main(String[] args) {
        int width = Animation.getAnimationWidth();
        int height = Animation.getAnimationHeight();
        Window window = new Window(width, height);
        window.showWindow();
        
        //------------------------------------------
        //we can split the animation processing for example to 4 threads, each thread will process its set of rows
        PaintingThread t1 = new PaintingThread(window, 0, height/4); //first 25% of rows for thread 1
        PaintingThread t2 = new PaintingThread(window, height/4, height/2); //second 25% of rows for thread 2
        PaintingThread t3 = new PaintingThread(window, height/2, (height/4)*3); //third 25% of rows for thread 3
        PaintingThread t4 = new PaintingThread(window, ((height/4)*3), height); //fourth 25% of rows for thread 4
        PaintingThread.numberOfThreads = 4; //set number of threads we will be using
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        //------------------------------------------
    }
  
}

class PaintingThread extends Thread {
	
	private final Window window; //animations window object
	private final int myFirstRow; //threads first row to process
	private final int myLastRow; //threads last row to process
	
	public static int numberOfThreads; //number of threads used for animation
	
	private static int frameIndex = 0; //index of current animation image
	private static int counter = 0; //help counter
	private static Object lock = new Object(); //object used for locking (synchronization)
	
	public PaintingThread(Window window, int myFirstRow, int myLastRow) {
		this.window = window;
		this.myFirstRow = myFirstRow;
		this.myLastRow = myLastRow;
	}
	
	@Override
	public void run() {
		while(true) {
			int animationWidth = Animation.getAnimationWidth();
			for(int x=0; x<animationWidth; x++) { //set pixels of all columns
				for(int y=myFirstRow; y<myLastRow; y++) { //set pixels of threads rows
					Color pixelColor = Animation.pixelColorAt(frameIndex, x, y); //get color from image
					window.setPixel(x, y, pixelColor); //set color on window
				}
			}
			
			synchronized(lock) { //synchronize on "lock object" shared between all threads
				counter++; //increment counter
				if(counter != numberOfThreads) { //if this thread is not a last thread
					try {
						lock.wait(); //just wait
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else { //else if this thread is last thread 
					counter = 0; //reset counter
					frameIndex++; //increment frame index for another iteration
					if(frameIndex == Animation.numberOfImages()) { //if there are no more images
						frameIndex = 0; //reset back to first image
					}
					window.repaintCanvas(); //repaint canvas of window
					lock.notifyAll(); //awake other 3 threads that are waiting
				}
			}
		}
	}
	
}
