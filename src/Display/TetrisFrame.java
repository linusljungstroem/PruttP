package Display;

import Pieces.Piece;
import kdksd.PlayingField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TetrisFrame extends JFrame {

    private TetrisDisplay display;
    private PiecePanel savedPiece;
    private PiecePanel[] pieceQueue = new PiecePanel[3];

    private JLabel pointsLabel;
    private PlayingField field;



    private int points = 0;
    private int time = 600;
    private int pointComp = 0;


    private Timer timer;

    private TetrisFrame() {


        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setLayout(new BorderLayout());

        field = new PlayingField();

        pointsLabel = new JLabel("Points: 0", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pointsLabel, BorderLayout.NORTH);

        display = new TetrisDisplay(field);
        add(display, BorderLayout.CENTER);

        savedPiece = new PiecePanel();
        savedPiece.setPreferredSize(new Dimension(150, 150));
        add(savedPiece,BorderLayout.WEST);

        JPanel pieceQueueContainer = new JPanel(new GridLayout(3,1,5,5));
        pieceQueueContainer.setPreferredSize(new Dimension(150,450));

        for(int i = 0; i < pieceQueue.length; i++){
            pieceQueue[i] = new PiecePanel(field.getQueue().get(i));
            pieceQueue[i].setSize(new Dimension(150,150));
            pieceQueueContainer.add(pieceQueue[i]);
        }

        add(pieceQueueContainer,BorderLayout.EAST);

        setVisible(true);




        timer = new Timer(600, e -> {

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
            updatePieceQueue();
            repaint();
        });

        timer.start();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new TetrisKeyListener());


    }


    private void updatePieceQueue() {
        for(int i = 0; i < 2; i++){
            pieceQueue[i].updatePiece(field.getQueue().get(i));
        }
    }

    private void restartGame() {
        points = 0; // Reset the score
        time = 600;
        pointsLabel.setText("Points: 0"); // Update the points display
        field.resetField(); // Reset the playing field (implement in your kdksd.PlayingField class)
        field.newPiece(); // Spawn a new piece
        timer.start(); // Restart the timer
        repaint(); // Refresh the panel
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


    // a bit buggy
    public void changeTime() {

        if(points-pointComp >= 100) {
            time = (int) (time / 1.5);
            pointComp += 100;
            timer.setDelay(time);
        }

    }


    private class TetrisKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                    field.holdPiece();
                    savedPiece.updatePiece(field.getHeldPiece().getFirst());
                    break;
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



    public static void main(String[] args) {
        new TetrisFrame();
    }

}
