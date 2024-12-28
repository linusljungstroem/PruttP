package Pieces;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class Piece {


    // Y COORDINATE / ROW IS int[0]
    // X COORDINATE / ROW IS int[1]
    public ArrayList<int[]> coordinates;
    public int[] pivot;


    public Color color;

    static public Piece createPiece() {

        Random rand = new Random();

        int i = rand.nextInt(7);

        if(i == 0) {
            return new TPiece();
        }
        else if(i == 1) {
            return new JPiece();
        }
        else if(i == 2) {
            return new LPiece();
        }
        else if(i == 3) {
            return new ZPiece();
        }
        else if(i == 4) {
            return new SPiece();
        }
        else if(i == 5) {
            return new BPiece();
        }

        return new IPiece();


    }



    // Rotate the piece 90° clockwise
    public boolean rotateRight(boolean[][] playingField) {
        return rotate(playingField, true);
    }

    // Rotate the piece 90° counterclockwise
    public boolean rotateLeft(boolean[][] playingField) {
        return rotate(playingField, false);
    }


    // WORKS BAD :)
    public boolean rotate(boolean[][] playingField, boolean clockwise) {
        // Create a new list to hold the rotated coordinates
        ArrayList<int[]> newCoordinates = new ArrayList<>();



        System.out.println("Pivot: " + Arrays.toString(pivot));
        System.out.println("Original Coordinates:");
        for (int[] coord : coordinates) {
            // Calculate the position relative to the pivot
            int relativeX = coord[0] - pivot[0];
            int relativeY = coord[1] - pivot[1];
            System.out.println("Relative: (" + relativeX + ", " + relativeY + ")");

            // Apply rotation formula
            int rotatedX = clockwise ? relativeY : -relativeY;
            int rotatedY = clockwise ? -relativeX : relativeX;
            System.out.println("Rotated: (" + rotatedX + ", " + rotatedY + ")");

            // Translate back to the absolute position
            int newX = rotatedX + pivot[0];
            int newY = rotatedY + pivot[1];
            System.out.println("New Absolute: (" + newX + ", " + newY + ")");

            // Check if the new position is valid (in bounds and not occupied)
            if (newX < 0 || newX >= playingField.length || newY < 0 || newY >= playingField[0].length
                    || playingField[newX][newY]) {
                System.out.println("Rotation failed at: (" + newX + ", " + newY + ")");
                return false; // Invalid rotation
            }

            // Add the new coordinate to the rotated list
            newCoordinates.add(new int[]{newX, newY});
        }

        // If all rotations are valid, update the piece's coordinates
        coordinates.clear();
        coordinates.addAll(newCoordinates);

        return true;
    }


    public ArrayList<int[]> getCoordinates() {
        return coordinates;
    }

    public void fall() {
        for (int[] i : coordinates) {
            i[0] += 1;
        }
    }

    public void moveRight() {
        for (int[] i : coordinates) {
            i[1] += 1;
        }
    }

    public void moveLeft() {
        for (int[] i : coordinates) {
            i[1] -= 1;
        }
    }

    @Override
    public String toString() {

        StringBuilder returnstring = new StringBuilder();

        for (int[] coordinate : coordinates) {
            for (int j = 0; j < 2; j++) {
                returnstring.append(coordinate[j]);
            }
            returnstring.append("\n");
        }
        return returnstring.toString();
    }



}


