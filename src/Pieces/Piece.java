package Pieces;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Piece {


    // Y COORDINATE / ROW IS int[0]
    // X COORDINATE / ROW IS int[1]
    public ArrayList<int[]> coordinates;

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

    /*public ArrayList<Integer[]> getLocations {

        for( )

    }*/

}


