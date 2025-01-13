package Display;

import Pieces.Piece;
import General.Square;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

class PiecePanel extends JPanel {

    private Piece piece;
    private Square[][] grid;
    private final int rows = 5;
    private final int cols = 5;

    PiecePanel(Piece piece) {
        this.piece = piece;
        setSize(150,150);
        setLayout(new GridLayout(rows, cols, 5, 5));
    }

    PiecePanel() {
        this.piece = null;
        setSize(150,150);
        setLayout(new GridLayout(rows, cols, 5, 5));
    }

    void updatePiece(Piece piece) {
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

        Square[][] grid = makeGrid();

        GridMaker.drawGrid(g, grid);
    }



}
