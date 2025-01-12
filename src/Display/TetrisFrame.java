package Display;

import kdksd.PlayingField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;

public class TetrisFrame extends JFrame {

    private static SoundPlayer backgroundMusic;

    private TetrisDisplay display;
    private PiecePanel savedPiece;
    private PiecePanel[] pieceQueue = new PiecePanel[3];

    private JLabel pointsLabel;
    private PlayingField field;


    private int quickFall;

    private int points = 0;
    private int time;
    private int pointComp = 0;


    private Timer timer;

    private TetrisFrame() {

        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLayout(new BorderLayout());

        DificultyDisplay dificulty = new DificultyDisplay(this);
        add(dificulty, BorderLayout.CENTER);

        setVisible(true);

        setFocusable(true);
        requestFocusInWindow();


        addKeyListener(new TetrisKeyListener());






    }


    private void updatePieceQueue() {
        for(int i = 0; i < pieceQueue.length; i++){
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
        String filename = "leaderboard.txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(points+System.lineSeparator());

        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
        }

        // Create the options for the dialog
        Object[] options = {"Restart", "Change difficulty", "Show leaderboard"};

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
        else if (choice == 2){
            LeaderDisplay.showleaderboard(filename);
        }
    }


    void startGame(String difficulty) {

        if (timer != null) {
            timer.stop(); // Stop any existing timer
            timer = null;
        }

        switch (difficulty) {
            case "hard" -> {
                time = 400;
                quickFall = 200;
            }
            case "medium" -> {
                time = 600;
                quickFall = 300;
            }
            case "easy" -> {
                time = 800;
                quickFall = 400;
            }
        }

        getContentPane().removeAll();


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


        timer = new Timer(time, _ -> {

            if(field.gameOver()){
                timer.stop();

                endingGame();

            } else if(field.check_kill_Piece()) {


                int newpoints = field.clearRows();
                if(newpoints > 0){
                    points += newpoints;
                    SoundPlayer.clear();

                }

                changeTime();
                pointsLabel.setText("Points: " + points);
                field.newPiece();


            } else if (!field.check_kill_Piece()) {
                field.fall();
            }
            updatePieceQueue();
            revalidate();
            repaint();
        });
        repaint();
        timer.start();




    }






    // a bit buggy
    public void changeTime() {

        if(points-pointComp >= 100) {
            time = (int) (time / 1.5);
            pointComp += 100;
            timer.setDelay(time);
        }
        quickFall = (int)(time/2);

    }


    private class TetrisKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(field == null) {
                return;
            }
            switch (e.getKeyCode()) {

                case KeyEvent.VK_DOWN:
                    timer.setDelay(quickFall);
                    break;
                case KeyEvent.VK_S:
                    field.holdPiece();
                    savedPiece.updatePiece(field.getHeldPiece().getFirst());
                    break;
                case KeyEvent.VK_LEFT: //ändrat så att att vi kallar på en specifik move metod, som returnerar om draget gick igenom
                    boolean possible_left = field.movingleft();
                    if (possible_left){
                        SoundPlayer.move();
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    boolean possible_right = field.movingright();
                    if(possible_right){
                        SoundPlayer.move();
                    }
                    break;
                case KeyEvent.VK_UP: //ändrat så att rotate skickar tillbaka true om den lyckas, isåfall spelas ljud
                    boolean poss_rot = field.rotate_left();
                    if(poss_rot){
                        SoundPlayer.rotate();
                    }
                    break;
                case KeyEvent.VK_E:
                    boolean poss_rot_c = field.rotate_right(); // Rotate the piece clockwise
                    if(poss_rot_c){
                        SoundPlayer.rotate();
                    }
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

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                timer.setDelay(time);
            }
        }
    }



    public static void main(String[] args) {

        new TetrisFrame();

    }

}