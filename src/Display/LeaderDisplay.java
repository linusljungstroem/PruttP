package Display;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderDisplay extends JFrame {
    public LeaderDisplay(String leaderboardFile){
        setTitle("Leaderboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Top 5 Scores",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(5, 1)); // 5 rows for top 5 scores
        scoresPanel.setBackground(Color.WHITE);

        add(scoresPanel, BorderLayout.CENTER);

        ArrayList<Integer> scores;
        scores = readscores(leaderboardFile);
        scores.sort(Collections.reverseOrder());


        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            JLabel scoreLabel = new JLabel((i + 1) + ". " + scores.get(i), SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            scoresPanel.add(scoreLabel);
        }
        add(scoresPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    private ArrayList<Integer> readscores(String filename){
        ArrayList<Integer> scores = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
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

    public static void showleaderboard(String filename){
        new LeaderDisplay(filename);
    }
}

