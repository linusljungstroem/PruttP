package Display;

import General.Square;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


abstract class GridMaker {

    private static final int blockSize = 30;

    static void drawGrid(Graphics g, Square[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                int xPos = x * blockSize;
                int yPos = y * blockSize;

                if (grid[y][x].isOccupied()) {
                    g.setColor(grid[y][x].getColor());
                    g.fillRect(xPos, yPos, blockSize, blockSize);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(xPos, yPos, blockSize, blockSize);
                    g.setColor(Color.LIGHT_GRAY); // Grid background
                    g.drawRect(xPos, yPos, blockSize, blockSize);
                }
            }
        }
    }

    static void drawGhost(Graphics g, ArrayList<int[]> ghostCoordinates) {
        g.setColor(new Color(200, 200, 200, 128)); // Ghost piece color
        for (int[] coord : ghostCoordinates) {
            int x = coord[1];
            int y = coord[0];
            g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
        }
    }
}
