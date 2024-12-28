import Pieces.Piece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PlayingField {


    private JFrame frame;
    private final Integer x_size = 10;
    private final Integer y_size = 20;
    public boolean[][] field;
    public boolean[][] deadField;
    public Piece current_Piece;

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

            String prutt = scan.nextLine();
            move(prutt);
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

    private void move(String righorleft) {
        Boolean direction;
        if (Objects.equals(righorleft, "R")) {
            direction = true;
        } else if (Objects.equals(righorleft, "L")) {
            direction = false;
        } else {return;}


        if (checkBounds(direction)) {

            if(direction) {
                this.erasePiece(current_Piece.coordinates);
                current_Piece.moveRight();
                this.updatePiece(current_Piece.getCoordinates());
            }
            else {
                this.erasePiece(current_Piece.coordinates);
                current_Piece.moveLeft();
                this.updatePiece(current_Piece.getCoordinates());
            }
        }
    }

    private boolean checkBounds(Boolean direction) {

        boolean possible = true;

        if (direction) {
            for (int[] i : current_Piece.coordinates){
                int x = i[1];

                if (x == x_size) {
                    possible = false;
                    break;
                }
            }
        }
        else {
            for (int[] i : current_Piece.coordinates){
                int x = i[1];

                if (x == 0) {
                    possible = false;
                    break;
                }
            }
        }
        return possible;

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

        current_Piece = Piece.createPiece();

        ArrayList<int[]> newPiece = current_Piece.getCoordinates();

        updatePiece(newPiece);

    }

    // I FLYTTAR
    private void updatePiece(ArrayList<int[]> new_coordinates) {
        for (int[] i : new_coordinates) {
            int x = i[1];
            int y = i[0];


            field[y][x] = true;
        }
    }

    public void fall() {

        this.erasePiece(current_Piece.coordinates);
        current_Piece.fall();
        this.updatePiece(current_Piece.getCoordinates());

    }

    private void erasePiece(ArrayList<int[]> coordinates) {
        for (int[] i : coordinates) {

            int x = i[1];
            int y = i[0];
            field[y][x] = false;
        }
    }

    private boolean check_kill_Piece() {

        ArrayList<int[]> coordinates = current_Piece.getCoordinates();


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

    public void clearRow() {

        for(boolean[] b : deadField) {

            for (boolean bb : b) {

            }
        }

    }



}

