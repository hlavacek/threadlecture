package com.accenture.ddc.exercises.advanced1;

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

public class Excercise1 {
    
    public static void main(String[] args) {
        int width = Animation.getAnimationWidth();
        int height = Animation.getAnimationHeight();
        Window window = new Window(width, height);
        window.showWindow();
        
        //------------------------------------------
        
        
        
        //------------------------------------------
    }
}
