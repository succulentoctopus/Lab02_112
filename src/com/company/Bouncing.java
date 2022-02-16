package com.company;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class Bouncing extends JPanel{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    public static final int RADIUS = 50;
    double positionX;
    double positionY;

    double positionXGreen;
    double positionYGreen;

    //Note: The following are not used yet, you should use them in writing your code.
    double velocityX;
    double velocityY;

    double velocityXGreen;
    double velocityYGreen;


    double accelerationX;
    double accelerationY;

    //challenges implemented:
    /*
    squish ball 1 when it hits a wall
    initial x acceleration for ball 1 when it shoots out of cannon
    ball 2 is subject to gravity (acts like a bouncy ball)
     */

    class Runner implements Runnable{
        public Runner()
        {
            //Position and velocity for first sphere
            positionX = 275;
            positionY = HEIGHT - 275;
            velocityX = 10;
            velocityY = -10;
            //Position and velocity for second sphere
            positionXGreen = 300;
            positionYGreen = HEIGHT - 300;
            velocityXGreen = 10;
            velocityYGreen = -5;
            accelerationY = 9.8;
            accelerationX = 50;


        }
        public void run()
        {
            //initial X acceleration when ball 1 gets shot out of cannon
            positionX += accelerationX;

            while(true){
                //your code here
                //Movement for ball 1
                positionX += velocityX;
                positionY += velocityY;

                //Bouncing for ball 1
                if (positionX < 0 || positionX+30 > 1024) {
                    velocityX = -velocityX;
                }
                if (positionY < 0 || positionY+30 > 768) {
                    velocityY = -velocityY;
                }

                //movement (which includes gravity) for ball 2

                positionXGreen += velocityXGreen;
                velocityYGreen += accelerationY;
                positionYGreen += velocityYGreen;

                if (positionXGreen < 0 || positionXGreen+30 > 1024) {
                    velocityXGreen = -velocityXGreen;
                }
                if (positionYGreen+30 > 768) {
                    velocityYGreen = -velocityYGreen;
                    positionYGreen -= accelerationY;
                }

                //don't mess too much with the rest of this method
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }
        }
    }

    public Bouncing(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Bouncing world = new Bouncing();
        frame.setContentPane(world);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //The cannon you see is actually *not* a photograph of a real cannon.
        //It's drawn by the following. 
        g.setColor(Color.ORANGE);
        int xpts[] = {75, 275, 275, 350, 325, 150};
        int ypts[] = {HEIGHT-50, HEIGHT-250, HEIGHT-275, HEIGHT- 175, HEIGHT-175, HEIGHT-25};
        g.fillPolygon(xpts, ypts, 6);

        g.setColor(Color.BLUE);
        g.fillOval(150, HEIGHT-200, 200, 200);

        //ball 1 drawn here
        g.setColor(Color.WHITE);
        g.drawOval((int)positionX, (int)positionY,  RADIUS,  RADIUS);
        //squish the sphere when it hits a wall
        if (positionX-30 <= 0 || positionX+30 >= 1024) {
            g.drawOval((int)positionX, (int)positionY, RADIUS/2, RADIUS);
        }
        if (positionY-30 <= 0 || positionY+30 >= 768) {
            g.drawOval((int)positionX, (int)positionY, RADIUS, RADIUS/2);
        }

        //ball 2 drawn here
        g.setColor(Color.GREEN);
        g.fillOval((int)positionXGreen, (int)positionYGreen, RADIUS, RADIUS);
    }
}