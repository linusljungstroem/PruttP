package Display;

import javax.swing.*;
import java.awt.*;

public class DificultyDisplay extends JPanel {

    private final TetrisFrame parent;


    DificultyDisplay(TetrisFrame parrent) {

        parent = parrent;

        setLayout(new GridLayout(4, 1, 10, 10));


        JLabel qLabel = new JLabel("Select Difficulty", SwingConstants.CENTER);
        qLabel.setFont(new Font("Arial", Font.BOLD, 18));

        add(qLabel);

        JButton easy = new JButton("Easy");
        easy.addActionListener(e -> startGame("easy"));
        add(easy);

        JButton medium = new JButton("Medium");
        easy.addActionListener(e -> startGame("medium"));
        add(medium);

        JButton hard = new JButton("Hard");
        easy.addActionListener(e -> startGame("hard"));
        add(hard);

    }

    private void startGame(String difficulty) {

        System.out.println(difficulty);

        parent.startGame(difficulty);

    }

    public static void main(String[] args) {

    }

}
