package Pong;

import java.awt.*;

/**
 * Denna klass skapar en PongPanel som är en utökad JPanel som man kan rita på
 */
class PongPanel extends javax.swing.JPanel {

    public void paintComponent(Graphics grafik) {
        super.paintComponent(grafik);
        for (Ball ball : Gravity.balls) {
            grafik.fillOval((int)ball.xPos - ball.size/2, (int)ball.yPos - ball.size/2, ball.size, ball.size);
        }
    }
}
