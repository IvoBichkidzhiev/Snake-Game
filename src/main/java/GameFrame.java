import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(GamePanel gamePanel) {
        String nameOfTheGame = (gamePanel instanceof EasyMode)
                ? "Easy Mode"
                : "Hard Mode";

        this.setTitle(nameOfTheGame);
        this.add(gamePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
