package com.accenture.ddc.exercises.advanced1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Animation {
    
    private static BufferedImage[] imageArray;
    static {
        File folder = new File("data/exercise1");
        File[] files = folder.listFiles();
        imageArray = new BufferedImage[files.length];
        for(int i=0; i<files.length; i++) {
            try {
                imageArray[i] = ImageIO.read(new File("data/exercise1/"+i+".jpg"));
            } catch (IOException ex) {}
        }
    }
    
    public static Color pixelColorAt(int imageNumber, int x, int y) {
        int rgb = Animation.imageArray[imageNumber].getRGB(x, y);
        return new Color(rgb);
    }
    
    public static int numberOfImages() {
        return imageArray.length;
    }
    
    public static int getAnimationHeight() {
        return imageArray[0].getHeight();
    }
    
    public static int getAnimationWidth() {
        return imageArray[0].getWidth();
    }
    
}
