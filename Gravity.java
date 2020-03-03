package Pong;

import javax.swing.*;

import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Denna klass är en simulation av gravitation
 */
public class Gravity {
    //make the gravitational constant bigG
    private final double bigG = 6.67 * Math.pow(10, -11);
    //Set the density of the balls
    private double density = Math.pow(10, 7);
    //Create an array of balls to store all balls in
    static ArrayList<Ball> balls = new ArrayList<>();

    private JPanel mainPanel;
    private PongPanel pongPanel = (PongPanel) mainPanel;

    /**
     * Denna metod är en konstruktor för klassen Pong() som senare visas i en JFrame i main
     */
    private Gravity() {
        //Initiate balls
        //Det här är ett exempel, om du vill testa att ange ett eget scenario gör du det här
        Ball earth = new Ball();
        earth.size = 100;
        balls.add(earth);
        for (double i = 0; i < 2 * Math.PI ; i += Math.PI){
            Ball moon = new Ball();
            moon.xPos += 200 * Math.cos(i);
            moon.yPos -= 200 * Math.sin(i);
            moon.yVel = -1 * Math.cos(i + Math.PI);
            moon.xVel = -1 * Math.sin(i + Math.PI);
            balls.add(moon);
        }

        //Define the iteration process
        Timer timer = new Timer(1, e -> {
            //For each ball update its velocity
            for (Ball ball : balls) {
                //For each other ball update the target balls velocity
                for (Ball otherBall : balls) {
                    if (ball != otherBall) {
                        double xDist = (otherBall.xPos - ball.xPos);
                        double yDist = (otherBall.yPos - ball.yPos);
                        double dist = Math.sqrt((xDist * xDist) + (yDist * yDist));
                        if (dist > ball.size / 2.0) {
                            //Calculate the mass of the ball affecting the target ball
                            double mass = (((Math.pow(otherBall.size/2.0, 3))*4*Math.PI) / 3.0) * density;
                            //Calculate the acceleration of the target ball with Newtons formula
                            double totalAcc = (mass * bigG) / (dist * dist);
                            //Update the targets ball velocity
                            ball.xVel += totalAcc * (xDist / dist);
                            ball.yVel += totalAcc * (yDist / dist);
                        }
                    }
                }
            }
            //Update the position of each ball based on their velocity
            for (Ball ball : balls) {
                ball.xPos += ball.xVel;
                ball.yPos += ball.yVel;
                //If the ball
                if (ball.yPos > 675 || ball.yPos < 0)
                    ball.yVel *= -1;
                if (ball.xPos > 1200 || ball.xPos < 0)
                    ball.xVel *= -1;
                pongPanel.repaint();
            }
        });
        //Start the iteration
        timer.start();
    }

    /**
     * Denna metod ritar alla komponenter (Bollar) på mainPanel
     */
    private void createUIComponents() {
        mainPanel = new PongPanel();
        pongPanel = (PongPanel) mainPanel;
    }

    /**
     * Denna metod visar panelen i en JFrame på skärmen
     * @param args String[]
     */
    public static void main(String[] args) {
        //Skapa ditt fönster
        String namn = "Pong";
        JFrame frame = new JFrame(namn);
        //Tala om att du vill kunna stänga ditt förnster med krysset i högra hörnet
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Ange storleken på ditt fönster och att det ska vara fast
        frame.setSize(1200, 700);
        frame.setResizable(false);
        //Positionera ditt fönster i mitten av skärmen
        frame.setLocationRelativeTo(null);

        //Skapa en instans av din den här klassen som hanterar din panel
        Gravity myForm = new Gravity();
        //Lägg in din panel i programfönstret
        frame.setContentPane(myForm.mainPanel);
        //Visa programfönstret på skärmen
        frame.setVisible(true);
    }
}
