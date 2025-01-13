package Display;

import javax.swing.*;

class DifficultyDisplay extends ButtonDisplay {


    DifficultyDisplay(TetrisFrame parent) {

        frame = parent;

        setLayout(layout);

        label = new JLabel("Select Difficulty", SwingConstants.CENTER);
        label.setFont(font);
        add(label);

        b1 = new JButton("Easy");
        b1.addActionListener(_ -> startGame("easy"));
        add(b1);

        b2 = new JButton("Medium");
        b2.addActionListener(_ -> startGame("medium"));
        add(b2);

        b3 = new JButton("Hard");
        b3.addActionListener(_ -> startGame("hard"));
        add(b3);

    }

    private void startGame(String difficulty) {

        frame.startGame(difficulty);
    }
}
