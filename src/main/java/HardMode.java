import javax.swing.*;
import java.awt.*;
import java.io.*;

public class HardMode extends GamePanel {

    static int DELAY = 100;

    @Override
    public void startGame() {
        timer = new Timer(DELAY, this);
        timer.start();
        newApple();
        running = true;
    }

    @Override
    public void draw(Graphics g) {
        if (running) {

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fill3DRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, true);
            }

            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 4, g.getFont().getSize());

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 28));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("Best Score: " + getBestScore(), (SCREEN_WIDTH - metrics3.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(new HardMode());
        }
    }


    public static int getBestScore() {
        int theRecord = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("recordHardMode.txt"));
            theRecord = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException | NullPointerException | NumberFormatException ignored) {
        }
        return theRecord;
    }

    public void speedUpTheGame() {
        if (DELAY >= 60) {
            DELAY -= 1;
            timer.setDelay(DELAY);
        }
    }

    @Override
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        speedUpTheGame();
    }

    @Override
    public void checkCollisions() {
        //check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                // game over
                running = false;
                break;
            }
        }

        if (x[0] < 0) {
            running = false;
        } else if (x[0] > SCREEN_WIDTH) {
            running = false;
        } else if (y[0] < 0) {
            running = false;
        } else if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void gameOver(GamePanel panel) {
        int theRecord = getBestScore();

        if (theRecord < applesEaten) {
            setTheNewRecord(panel);
        } else {
            panels(panel);
        }
    }

    @Override
    public void setTheNewRecord(GamePanel panel) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("recordHardMode.txt"));
            writer.write("" + applesEaten);
            writer.close();
            JOptionPane.showMessageDialog(null, "You've set a new record of " + applesEaten + " scores!", "Congratulations!", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | NullPointerException | NumberFormatException ignored) {
        } finally {
            panels(panel);
        }
    }

}
