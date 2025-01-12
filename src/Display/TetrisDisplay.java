package Display;

import kdksd.PlayingField;
import kdksd.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TetrisDisplay extends JPanel {

    private PlayingField field;


    // needs difficulty
    public TetrisDisplay(PlayingField field) {

        this.field = field;
        setLayout(null);



    }

    // Projicera "spökbit"
    private void projectPiece() {

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
    }
}
