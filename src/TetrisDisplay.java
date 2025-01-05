import kdksd.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TetrisDisplay extends JPanel {

    private PlayingField field;
    private Timer timer;
    private JLabel pointsLabel;
    private int points = 0;

    public TetrisDisplay(PlayingField playingField) {

        this.field = playingField;

        setLayout(new BorderLayout());

        pointsLabel = new JLabel("Points: " + points);
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pointsLabel, BorderLayout.SOUTH); // Place label at the top


        timer = new Timer(200, e -> {
            if(field.check_kill_Piece()) {

                points += field.clearRows();

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
                    System.out.println("Color setting in paintComponent" + grid[y][x].getColor());
                    g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                } else {
                    g.setColor(Color.LIGHT_GRAY); // Grid background
                    g.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
                }
            }
        }
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
                case KeyEvent.VK_W:
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
            // Create a test frame
            JFrame frame = new JFrame("Test Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 800);

            PlayingField field1 = new PlayingField();

            // Create your display class instance (replace with your custom class)
            JPanel displayPanel = new TetrisDisplay(field1); // Replace with your class
            frame.add(displayPanel);


            frame.setVisible(true);

            Scanner scanner = new Scanner(System.in);



            while(true) {

                String rl = scanner.nextLine();

                field1.move(rl);
                field1.fall();

                displayPanel.repaint();

            }


    }




}
