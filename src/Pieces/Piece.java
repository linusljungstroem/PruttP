package Pieces;

import General.Square;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Piece {


    // Y COORDINATE / ROW IS int[0]
    // X COORDINATE / ROW IS int[1]
    ArrayList<int[]> coordinates;
    int[] pivot;
    final Color color;

    protected Piece(Color color) { // Never used
        this.color = color;
    }

    public Piece copy() {

        return Piece.createPiece(this.getClass().getSimpleName().replace("Piece", ""));
    }


    public abstract ArrayList<int[]> getPaintCoordinates();


    public static Piece createPiece() {

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

    // Endast för copy
    private static Piece createPiece(String type) {
        return switch (type) {
            case "I" -> new IPiece();
            case "O" -> new OPiece();
            case "T" -> new TPiece();
            case "S" -> new SPiece();
            case "Z" -> new ZPiece();
            case "J" -> new JPiece();
            case "L" -> new LPiece();
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }




    public boolean rotateRight(Square[][] playingField) {
        return rotate(playingField, true);
    }

    public boolean rotateLeft(Square[][] playingField) {return rotate(playingField, false);}


    private boolean rotate(Square[][] playingField, boolean clockwise) {
        ArrayList<int[]> newCoordinates = new ArrayList<>();

        for (int[] coord : coordinates) {

            int relativeY = coord[0] - pivot[0]; // Y = coord[0]
            int relativeX = coord[1] - pivot[1]; // X = coord[1]


            int rotatedY, rotatedX;
            if (clockwise) {
                rotatedX = relativeY;     // x' = y
                rotatedY = -relativeX;    // y' = -x
            } else {
                rotatedX = -relativeY;    // x' = -y
                rotatedY = relativeX;     // y' = x
            }


            int newY = rotatedY + pivot[0];
            int newX = rotatedX + pivot[1];


            if (newY < 0 || newY >= playingField.length || newX < 0 || newX >= playingField[0].length
                    || playingField[newY][newX].isOccupied()) {
                return false;
            }

            newCoordinates.add(new int[]{newY, newX});
        }

        for (int i = 0; i < coordinates.size(); i++) {
            coordinates.get(i)[0] = newCoordinates.get(i)[0];
            coordinates.get(i)[1] = newCoordinates.get(i)[1];
        }

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

    public Color getColor() {return color;}

    // Endast för tester
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


}


