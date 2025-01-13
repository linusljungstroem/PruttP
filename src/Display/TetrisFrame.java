package Display;

import General.PlayingField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;

public class TetrisFrame extends JFrame {

    // För tetrislåten
    private static SoundPlayer backgroundMusic;

    private TetrisDisplay display;
    private PiecePanel savedPiece;
    private PiecePanel[] pieceQueue = new PiecePanel[3];

    private JLabel pointsLabel;
    private PlayingField field;


    private int quickFall = 100;
    private int points = 0;
    private int time;
    private int pointComp = 0;

    private int pointMod;


    private Timer timer;

    private TetrisFrame() {

        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLayout(new BorderLayout());

        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new TetrisKeyListener());
        showDifficulties();

    }


    private void updatePieceQueue() {
        for(int i = 0; i < pieceQueue.length; i++){
            pieceQueue[i].updatePiece(field.getQueue().get(i));
        }
    }


    private void endingGame() {

        getContentPane().removeAll();

        String filename = "src/wav/leaderboard.txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(points+System.lineSeparator());

        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
        }

        add(new GameOverDisplay(this));

        repaint();
    }

    void showLeaderBoard() {

        getContentPane().removeAll();
        add(new LeaderDisplay(this));
        revalidate();
        repaint();
    }

    void startGame(String difficulty) {

        if (timer != null) {
            timer.stop();
            timer = null;
        }

        switch (difficulty) {
            case "hard" -> {
                time = 400;
                pointMod = 4;
            }
            case "medium" -> {
                time = 600;
                pointMod = 2;
            }
            case "easy" -> {
                time = 800;
                pointMod = 1;
            }
        }

        points = 0;

        getContentPane().removeAll();

        field = new PlayingField();

        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
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

        revalidate();

        timer = new Timer(time, _ -> {

            if(field.gameOver()){
                timer.stop();
                endingGame();

            } else if(field.check_kill_Piece()) {


                int newpoints = field.clearRows();
                if(newpoints > 0){
                    points += newpoints * pointMod;
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

    private void changeTime() {

        if(points-pointComp >= 100) {
            time = (int) (time / 1.5);
            pointComp += 100;
            timer.setDelay(time);
            if(time <= 100) {
                quickFall = (int) (time*0.8);
            }
        }
    }

    void showDifficulties() {
        getContentPane().removeAll();
        DifficultyDisplay difficulty = new DifficultyDisplay(this);
        add(difficulty, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    void quitGame() {
        dispose();
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
                case KeyEvent.VK_LEFT:
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
                case KeyEvent.VK_UP:
                    boolean poss_rot = field.rotate_left();
                    if(poss_rot){
                        SoundPlayer.rotate();
                    }
                    break;
                case KeyEvent.VK_E:
                    boolean poss_rot_c = field.rotate_right();
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
                    break;
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                timer.setDelay(time);
            }
        }
    }



    public static void main(String[] args) {

        // För tetrislåten
        //SoundPlayer.music();
        new TetrisFrame();
    }
}