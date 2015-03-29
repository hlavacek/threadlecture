package com.accenture.ddc.exercises.advanced1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
    
    private final JFrame frame;
    private final BufferedImage canvas;
    private final Dimension windowSize = new Dimension(Animation.getAnimationWidth(), Animation.getAnimationHeight());
    private final JPanel panel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(canvas, 0, 0, null);
        }
    };
    
    public Window(int x, int y) {
        frame = new JFrame("Animation exercise");
        canvas = new BufferedImage(Animation.getAnimationWidth(), Animation.getAnimationHeight(), BufferedImage.TYPE_INT_ARGB);
    }
    
    public void showWindow() {
        frame.setResizable(false);
        frame.setMinimumSize(windowSize);
        frame.setMaximumSize(windowSize);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void setPixel(int x, int y, Color color) {
        canvas.setRGB(x, y, color.getRGB());
    }
    
    public void repaintCanvas() {
        panel.repaint();
    }
}