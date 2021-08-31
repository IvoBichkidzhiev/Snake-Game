import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Random;

public abstract class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 1200;
    static final int SCREEN_HEIGHT = 750;
    static int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;

    boolean running = false;
    Timer timer;
    Random random;
    static ArrayDeque<Character> directions;
    static Character currentDirection;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        directions = new ArrayDeque<>();
        directions.add('R');
        startGame();
    }

    public abstract void startGame();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public abstract void draw(Graphics g);

    public static int getBestScore() {
        return -1;
    }

    public abstract void newApple();

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (!directions.isEmpty()) {
            currentDirection = directions.pop();
        }

        if (currentDirection == 'U') {
            y[0] = y[0] - UNIT_SIZE;
        } else if (currentDirection == 'D') {
            y[0] = y[0] + UNIT_SIZE;
        } else if (currentDirection == 'L') {
            x[0] = x[0] - UNIT_SIZE;
        } else if (currentDirection == 'R') {
            x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public abstract void checkCollisions();

    public abstract void gameOver(GamePanel panel);

    public abstract void setTheNewRecord(GamePanel panel);

    protected void panels(GamePanel gamePanel) {
        String[] responses = {"Try again!", "I'll give up.", "Back to Main Menu"};

        int tryAgainOrNot = JOptionPane.showOptionDialog(
                null,
                "Do you want one more game?",
                "Continue or giving up?",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                responses,
                null
        );

        if (tryAgainOrNot == 0) {
            SnakeGame.gameFrame.dispose();
            SnakeGame.gameFrame = new GameFrame(gamePanel);
        } else if (tryAgainOrNot == 1) {
            int doYouWantToQuit = JOptionPane.showConfirmDialog(null, "Do you really want to quit the game?", "WARNING", JOptionPane.YES_NO_OPTION);

            if (doYouWantToQuit == 0) {
                System.exit(0);
            } else {
                panels(gamePanel);
            }

        } else if (tryAgainOrNot == 2) {
            SnakeGame.gameFrame.dispose();
            new MainMenu();
        } else {
            panels(gamePanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public static class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (currentDirection != 'R') {
                        directions.add('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (currentDirection != 'L') {
                        directions.add('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (currentDirection != 'D') {
                        directions.add('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (currentDirection != 'U') {
                        directions.add('D');
                    }
                    break;
            }

        }
    }
}
