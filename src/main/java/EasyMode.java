import javax.swing.*;
import java.awt.*;
import java.io.*;

public class EasyMode extends GamePanel {

    static int DELAY = 120;

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

            for (int i = 0; i < SCREEN_HEIGHT / 2; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i <= 3) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.green);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 4, g.getFont().getSize());

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 28));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("Best Score: " + getBestScore(), (SCREEN_WIDTH - metrics3.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(new EasyMode());
        }
    }

    public void gameOver(GamePanel panel) {
        int theRecord = getBestScore();

        if (theRecord < applesEaten) {
            setTheNewRecord(panel);
        } else {
            panels(panel);
        }
    }

    public static int getBestScore() {
        int theRecord = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("recordEasyMode.txt"));
            theRecord = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException | NullPointerException | NumberFormatException ignored) {
        }
        return theRecord;
    }

    @Override
    public void newApple() {
        int number1 = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        int number2 = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

        for (int i = 1; i < bodyParts; i++) {
            if (x[i] != number1 && y[i] != number2) {
                appleX = number1;
                appleY = number2;
                // break;
            }
        }
    }

    @Override
    public void checkCollisions() {
        //check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }

        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH;
        } else if (x[0] > SCREEN_WIDTH) {
            x[0] = 0;
        } else if (y[0] < 0) {
            y[0] = SCREEN_HEIGHT;
        } else if (y[0] > SCREEN_HEIGHT) {
            y[0] = 0;
        }

        if (!running) {
            timer.stop();
        }

    }

    @Override
    public void setTheNewRecord(GamePanel panel) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("recordEasyMode.txt"));
            writer.write("" + applesEaten);
            writer.close();
            JOptionPane.showMessageDialog(null, "You've set a new record of " + applesEaten + " scores!", "Congratulations!", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | NullPointerException | NumberFormatException ignored) {
        } finally {
            panels(panel);
        }
    }


}
