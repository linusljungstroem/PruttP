package Display;

import General.PlayingField;
import General.Square;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class TetrisDisplay extends JPanel {

    private PlayingField field;

    TetrisDisplay(PlayingField field) {

        this.field = field;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Square[][] grid = field.getField();

        // Use the utility to draw the grid
        GridMaker.drawGrid(g, grid);

        // Draw the ghost piece
        ArrayList<int[]> ghostCoordinates = field.ghost_coordinates();
        GridMaker.drawGhost(g, ghostCoordinates);
    }

}
