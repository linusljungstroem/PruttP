package Display;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class LeaderDisplay extends JPanel {

    private static final int rows = 5;
    private static final String leaderboardFile = "src/wav/leaderboard.txt";
    private TetrisFrame parent;

    private Font font = new Font("Arial", Font.BOLD, 20);


    LeaderDisplay(TetrisFrame parent){

        this.parent = parent;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Top 5 Scores",SwingConstants.CENTER);
        title.setFont(font);
        add(title, BorderLayout.NORTH);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(rows, 1));
        scoresPanel.setBackground(Color.WHITE);

        add(scoresPanel, BorderLayout.CENTER);

        ArrayList<Integer> scores;
        scores = readscores();
        scores.sort(Collections.reverseOrder());


        for (int i = 0; i < Math.min(rows, scores.size()); i++) {
            JLabel scoreLabel = new JLabel((i + 1) + ". " + scores.get(i), SwingConstants.CENTER);
            scoreLabel.setFont(font);
            scoresPanel.add(scoreLabel);
        }
        add(scoresPanel,BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton goBack = new JButton("Play again");
        goBack.addActionListener(_ -> playAgain());
        panel.add(goBack);

        JButton quit = new JButton("Quit");
        quit.addActionListener(_ -> quitGame());
        panel.add(quit);

        add(panel, BorderLayout.SOUTH);
    }

    private ArrayList<Integer> readscores(){
        ArrayList<Integer> scores = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))){
            String line;
            while((line = reader.readLine())!= null){
                scores.add(Integer.parseInt(line));
            }
        }
        catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
        return scores;
    }

    private void playAgain() {
        parent.showDifficulties();
    }

    private void quitGame() {
        parent.quitGame();
    }




    // Bara för test
    public static void main(String[] args) {
        JFrame frame = new JFrame("TETE");
        frame.setSize(1000,1000);

        //frame.add(new LeaderDisplay());

        frame.setVisible(true);


    }
}

