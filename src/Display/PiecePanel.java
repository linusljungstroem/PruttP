package Display;

import Pieces.JPiece;
import Pieces.Piece;
import kdksd.Square;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import kdksd.Square;
import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class PiecePanel extends JPanel {
    private Piece piece; // Holds either the queue of pieces or a single stored piece
    private Square[][] grid;
    private final int rows = 5;
    private final int cols = 5;

    public PiecePanel(Piece piece) {
        this.piece = piece;
        setSize(150,150);
        setLayout(new GridLayout(rows, cols, 5, 5));
    }

    public PiecePanel() {
        this.piece = null;
    }

    public void updatePiece(Piece piece) {
        this.piece = piece;

        repaint();
    }


    private Square[][] makeGrid() {

        grid = new Square[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                Square sqr = new Square();
                grid[i][j] = sqr;
            }
        }
        if(this.piece == null) {
            return grid;
        }

        ArrayList<int[]> coordinates = this.piece.getPaintCoordinates();
        Color pieceColor = this.piece.getColor();

        for(int[] coord : coordinates) {
            int y = coord[0];
            int x = coord[1];

            grid[y][x].setOccupied(pieceColor);
        }
        return grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 30;
        Square[][] grid = makeGrid();

        // Draw grid
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                int xPos = x * cellSize;
                int yPos = y * cellSize;

                if (grid[y][x].isOccupied()) {
                    g.setColor(grid[y][x].getColor());
                    g.fillRect(xPos, yPos, cellSize, cellSize);
                } else {
                    g.setColor(Color.BLACK);
                    g.drawRect(xPos, yPos, cellSize, cellSize);
                }
            }
        }
    }


}
