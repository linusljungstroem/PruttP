import Pieces.Piece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PlayingField {


    private JFrame frame;
    private final Integer x_size = 10;
    private final Integer y_size = 20;
    public boolean[][] field;
    public boolean[][] deadField;
    public Piece falling_Piece;

    Scanner scan = new Scanner(System.in);

    protected PlayingField() {

        initField();
        manualUpdate();

    }

    private void manualUpdate() {
        newPiece();

        while (true) {
            System.out.println(this);

            if(check_kill_Piece()) {
                newPiece();
                System.out.println("NEW PIECE!");
            }
            scan.nextLine();
            fall();
        }
    }


    private void initField() {

        field = new boolean[y_size][x_size];
        deadField = new boolean[y_size][x_size];
        for(int i = 0; i < y_size; i++) {
            for(int j = 0; j < x_size; j++) {
                field[i][j] = false;
                deadField[i][j] = false;
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder print = new StringBuilder();


        for(int i = 0; i < y_size; i++) {

            print.append("| ");

            for (int j = 0; j < x_size; j++) {
                if (field[i][j]) {
                    print.append("  X  ");

                } else {
                    print.append("  O  ");
                }
            }
            print.append(" |\n");
        }


        return print.toString();
    }

    public void newPiece() {

        falling_Piece = Piece.createPiece();

        ArrayList<int[]> newPiece = falling_Piece.getCoordinates();

        updatePiece(newPiece);

    }

    // BARA FÖR ATT STOPPA IN EN NY BIT I SPELPLANEN
    private void updatePiece(ArrayList<int[]> new_coordinates) {
        for (int[] i : new_coordinates) {
            int x = i[1];
            int y = i[0];


            field[y][x] = true;
        }
    }

    public void fall() {

        this.erasePiece(falling_Piece.coordinates);
        falling_Piece.fall();
        this.updatePiece(falling_Piece.getCoordinates());

    }

    private void erasePiece(ArrayList<int[]> coordinates) {
        for (int[] i : coordinates) {

            int x = i[1];
            int y = i[0];
            field[y][x] = false;
        }
    }

    private boolean check_kill_Piece() {

        ArrayList<int[]> coordinates = falling_Piece.getCoordinates();


        for (int[] i : coordinates) {

            int x = i[1];
            int y = i[0];

            if ( y == y_size-1) {

                updateDeadField(coordinates);
                return true;
            } else if ( deadField[ y + 1 ][ x ] ) {

                updateDeadField(coordinates);
                return true;
            }
        }

        return false;
    }

    public void updateDeadField(ArrayList<int[]> coordinates) {

        for(int[] i : coordinates) {

            int x = i[1];
            int y = i[0];
            deadField[y][x] = true;
        }

    }



}

