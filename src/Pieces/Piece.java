package Pieces;

import kdksd.Square;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public abstract class Piece {


    // Y COORDINATE / ROW IS int[0]
    // X COORDINATE / ROW IS int[1]
    public ArrayList<int[]> coordinates;
    public int[] pivot;
    protected final Color color;

    protected Piece(Color color) { // Never used
        this.color = color;
    }


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
            return new OPiece();
        }

        return new IPiece();


    }

    static public Piece createPiece(String shape) {

        if(Objects.equals(shape, "T")) {
            return new TPiece();
        }
        else if(shape == "J") {
            return new JPiece();
        }
        else if(shape == "L") {
            return new LPiece();
        }
        else if(shape == "Z") {
            return new ZPiece();
        }
        else if(shape == "S") {
            return new SPiece();
        }
        else if(shape == "O") {
            return new OPiece();
        }

        return new IPiece();
    }



    // Rotate the piece 90° clockwise
    public void rotateRight(Square[][] playingField) {
        rotate(playingField, true);
    }

    // Rotate the piece 90° counterclockwise
    public void rotateLeft(Square[][] playingField) {
        rotate(playingField, false);
    }


    // WORKS GREJT :)
    public void rotate(Square[][] playingField, boolean clockwise) {
        ArrayList<int[]> newCoordinates = new ArrayList<>();

        for (int[] coord : coordinates) {
            // Step 1: Calculate relative position to the pivot
            int relativeY = coord[0] - pivot[0]; // Y = coord[0]
            int relativeX = coord[1] - pivot[1]; // X = coord[1]

            // Step 2: Apply rotation matrix
            int rotatedY, rotatedX;
            if (clockwise) {
                rotatedX = relativeY;     // x' = y
                rotatedY = -relativeX;    // y' = -x
            } else {
                rotatedX = -relativeY;    // x' = -y
                rotatedY = relativeX;     // y' = x
            }

            // Step 3: Translate back to absolute position
            int newY = rotatedY + pivot[0];
            int newX = rotatedX + pivot[1];

            // Step 4: Validate new position
            if (newY < 0 || newY >= playingField.length || newX < 0 || newX >= playingField[0].length
                    || playingField[newY][newX].isOccupied()) {
                return; // Stop rotation
            }

            newCoordinates.add(new int[]{newY, newX});
        }

        // Step 5: Update coordinates if all checks passed
        for (int i = 0; i < coordinates.size(); i++) {
            coordinates.get(i)[0] = newCoordinates.get(i)[0];
            coordinates.get(i)[1] = newCoordinates.get(i)[1];
        }


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
                returnstring.append(" ");
            }
            returnstring.append("\n");
        }
        return returnstring.toString();
    }

    public Color getColor() {
        return color;
    }

}


