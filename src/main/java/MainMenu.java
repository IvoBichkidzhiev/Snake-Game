import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {

    JButton button1;
    JButton button2;
    JButton button3;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;

    MainMenu() {
        createFirstLabel();
        createSecondLabel();
        createThirdLabel();
        createFourthLabel();

        createFirstButton();
        createSecondButton();
        createThirdButton();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);
        this.setSize(600, 500);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.black);

        this.add(button1);
        this.add(button2);
        this.add(button3);

        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);
    }

    private void createFourthLabel() {
        label4 = new JLabel();
        label4.setText(String.format("%s: %d", "Best", HardMode.getBestScore()));
        label4.setBounds(302, 280, 500, 100);
        label4.setVisible(true);
        label4.setFont(new Font("Ink Free", Font.BOLD, 25));
        label4.setForeground(Color.red);
    }

    private void createThirdLabel() {
        label3 = new JLabel();
        label3.setText(String.format("%s: %d", "Best", EasyMode.getBestScore()));
        label3.setBounds(102, 280, 500, 100);
        label3.setVisible(true);
        label3.setFont(new Font("Ink Free", Font.BOLD, 25));
        label3.setForeground(Color.green);
    }

    private void createSecondLabel() {
        label2 = new JLabel();
        label2.setText(String.format("%s: ", "Choose the level of difficulty"));
        label2.setBounds(10, 100, 500, 100);
        label2.setVisible(true);
        label2.setFont(new Font("Ink Free", Font.BOLD, 35));
        label2.setForeground(Color.ORANGE);
    }

    private void createFirstLabel() {
        label1 = new JLabel();
        label1.setText(String.format("%s!", "Welcome, Player"));
        label1.setBounds(10, 10, 500, 100);
        label1.setVisible(true);
        label1.setFont(new Font("Ink Free", Font.BOLD, 45));
        label1.setForeground(Color.ORANGE);
    }

    private void createThirdButton() {
        button3 = new JButton();
        button3.setBounds(100, 360, 300, 30);
        button3.addActionListener(this);
        button3.setText("Quit");
        button3.setFocusable(false);
        button3.setFont(new Font("Tahoma", Font.BOLD, 20));
        button3.setForeground(Color.black);
        button3.setBackground(Color.gray);
    }

    private void createSecondButton() {
        button2 = new JButton();
        button2.setBounds(300, 200, 100, 100);
        button2.addActionListener(this);
        button2.setText("Hard");
        button2.setFocusable(false);
        button2.setFont(new Font("Tahoma", Font.BOLD, 25));
        button2.setForeground(Color.red);
        button2.setBackground(Color.white);
    }

    private void createFirstButton() {
        button1 = new JButton();
        button1.setBounds(100, 200, 100, 100);
        button1.addActionListener(this);
        button1.setText("Easy");
        button1.setFocusable(false);
        button1.setFont(new Font("Tahoma", Font.BOLD, 25));
        button1.setForeground(Color.green);
        button1.setBackground(Color.white);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            SnakeGame.gameFrame = new GameFrame(new EasyMode());
            this.dispose();
        } else if (e.getSource() == button2) {
            SnakeGame.gameFrame = new GameFrame(new HardMode());
            this.dispose();
        } else if (e.getSource() == button3) {
            System.exit(0);
        }

    }
}


