package Display;

import javax.swing.*;

class GameOverDisplay extends ButtonDisplay {

    GameOverDisplay(TetrisFrame parent) {

        frame = parent;

        setLayout(layout);

        label = new JLabel("Game over", SwingConstants.CENTER);
        label.setFont(font);
        add(label);


        b1 = new JButton("Change difficulty");
        b1.addActionListener(_ -> changeDifficulty());
        add(b1);

        b2 = new JButton("Show leaderboard");
        b2.addActionListener(_ -> showLeaderboard());
        add(b2);

        b3 = new JButton("Quit");
        b3.addActionListener(_ -> quit());
        add(b3);


    }

    private void quit() {
        frame.quitGame();
    }

    private void showLeaderboard() {
        frame.showLeaderBoard();
    }

    private void changeDifficulty() {
        frame.showDifficulties();
    }

}
