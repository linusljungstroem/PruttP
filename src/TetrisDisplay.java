import kdksd.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TetrisDisplay extends JPanel {

    private PlayingField field;
    private Timer timer;
    private JLabel pointsLabel;
    private int points = 0;
    private int time = 600;
    private int pointComp = 0;

    // needs difficulty
    public TetrisDisplay() {

        this.field = new PlayingField();

        setLayout(new BorderLayout());

        pointsLabel = new JLabel("Points: " + points);
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pointsLabel, BorderLayout.SOUTH); // Place label at the top

        int temp = 0;

        timer = new Timer(350, e -> {

            if(field.gameOver()){
                timer.stop();
                endingGame();

            } else if(field.check_kill_Piece()) {

                points += field.clearRows();

                changeTime();

                pointsLabel.setText("Points: " + points);

                field.newPiece();

            } else if (!field.check_kill_Piece()) {
                field.fall();
            }
            repaint();
        });

        timer.start();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new TetrisKeyListener());


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int blockSize = 30; // Size of each block in pixels
        Square[][] grid = field.getField();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x].isOccupied()) {
                    g.setColor(grid[y][x].getColor()); // Occupied cell color
                    g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                } else {
                    g.setColor(Color.LIGHT_GRAY); // Grid background
                    g.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
                }
            }
        }

        // paint saved block and next block
    }


    // Inner class for handling keyboard input
    private class TetrisKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    field.move("L"); // Move the piece left
                    break;
                case KeyEvent.VK_RIGHT:
                    field.move("R"); // Move the piece right
                    break;
                case KeyEvent.VK_UP:
                    field.rotate("K"); // Move the piece down faster
                    break;
                case KeyEvent.VK_E:
                    field.rotate("T"); // Rotate the piece clockwise
                    break;
                case KeyEvent.VK_SPACE:
                    while(!field.check_kill_Piece()) {
                        field.fall();
                    }
                    break;
                default:
                    // Handle other keys if necessary
                    break;
            }

            repaint(); // Repaint after every key press
        }
    }

    // a bit buggy
    public void changeTime() {
        System.out.println(time + "TIME");

        if(points-pointComp >= 100) {
            time = (int) (time / 1.5);
            pointComp = points-pointComp;
            timer.setDelay(time);
        }
        System.out.println(time + "NEWTIME");
    }

    private void endingGame() {
        // Create the options for the dialog
        Object[] options = {"Restart", "Change difficulty"};
        
        // Show the custom dialog
        int choice = JOptionPane.showOptionDialog(
                this,
                "Game Over! Your score: " + points,
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, // No custom icon
                options, // The options for the buttons
                options[0] // Default button
        );
        if (choice == 0) { // Restart button clicked
            restartGame(); // Restart the game
        } else if (choice == 1) { // Another Action button clicked
            // Placeholder for future functionality
            System.out.println("Another Action chosen!");
        }
    }

    private void restartGame() {
        points = 0; // Reset the score
        time = 600;
        pointsLabel.setText("Points: 0"); // Update the points display
        field.resetField(); // Reset the playing field (implement in your PlayingField class)
        field.newPiece(); // Spawn a new piece
        timer.start(); // Restart the timer
        repaint(); // Refresh the panel
    }



    public static void main(String[] args) {
            // Create a test frame
            JFrame frame = new JFrame("Test Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 800);

            JPanel displayPanel = new TetrisDisplay(); // Replace with your class
            frame.add(displayPanel);

            frame.setVisible(true);

    }


}
