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


    // Omgjord för att bara testa rotate
    protected PlayingField() {

        initField();
        rotTEST("HUGO");

    }

    // Samma som manualUpdate fast den bara gjord för att testa Rotate
    private void rotTEST(String shape) {
        newPiece(shape);
        System.out.println(current_Piece);

        while (true) {


            fall();
            fall();
            fall();
            while (true) {
                System.out.println("THE NORMAL PRINT\n" + this);

                String prutt = scan.nextLine();


                rotate(prutt);
            }
        }
    }

    // Första testmetoden
    private void manualUpdate() {

        newPiece();
        System.out.println(current_Piece);

        while (true) {

            System.out.println("THE NORMAL PRINT\n" + this);

            String prutt = scan.nextLine();

            move(prutt);

            rotate(prutt);

            fall();

            if(check_kill_Piece()) {
                clearRows();
                newPiece();
                System.out.println("NEW PIECE \n" + current_Piece);
            }
        }
    }

    private void rotate(String rightorleft) {

        if (Objects.equals(rightorleft, "T")) {

            System.out.println("ROTATE RIGHT CALL");

            System.out.println("before rotate \n" + current_Piece);

            this.erasePiece(current_Piece.coordinates);
            current_Piece.rotateRight(field);
            this.updatePiece(current_Piece.getCoordinates());

            System.out.println(current_Piece);

        } else if (Objects.equals(rightorleft, "K")) {

            System.out.println("ROTATE LEFT CALL");

            System.out.println("before rotate \n" + current_Piece);

            this.erasePiece(current_Piece.coordinates);
            current_Piece.rotateLeft(field);
            this.updatePiece(current_Piece.getCoordinates());

            System.out.println(current_Piece);
        }
        else {
            return;
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

            if (direction) {
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

                if (x == x_size-1) {
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

    public String printDeadField() {

        StringBuilder print = new StringBuilder();


        for(int i = 0; i < y_size; i++) {

            print.append("| ");

            for (int j = 0; j < x_size; j++) {
                if (deadField[i][j]) {
                    print.append("  X  ");

                } else {
                    print.append("  O  ");
                }
            }
            print.append(" |\n");
        }


        return print.toString();
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

    // Vanliga konstruktorn
    public void newPiece() {

        current_Piece = Piece.createPiece();
        ArrayList<int[]> newPiece = current_Piece.getCoordinates();
        updatePiece(newPiece);

    }

    // Konstsruktor där vi kan välja vilken form biten ska ha
    public void newPiece(String shape) {

        current_Piece = Piece.createPiece(shape);
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
    // RETURNS FALSE TRUE
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

    public void updateClearRows() {
        for(int i = 0; i < y_size; i++) {
            for (int j = 0; j < x_size; j++) {
                if(field[i][j]) {
                    deadField[i][j] = true;
                }
            }
        }
    }

    public void clearRows() {

        boolean truerow;
        ArrayList<Integer> listofrows = new ArrayList<>();


        // FOR LOOP CHECKS WHICH ROWS ARE FULLY TRUE -- MEANS THEY SHOULD BE ERASED
        for(int i = y_size-1; i >= 0; i--) {

            truerow = true;

            for(boolean b : deadField[i]) {
                if (!b) {
                    truerow = false;
                    break;
                }
            }
            if(truerow) {
                listofrows.add(i);
            }
        }

        // CLEARS THE ROWS TO BE CLEARED
        for(int i : listofrows) {

            for (int j = 0; j < x_size; j++) {
                field[i][j] = false;
            }
        }

        int counter = 0;

        // MOVES REMAINING ROWS DOWNWARDS
        for (int i : listofrows) {

            for (int rad = i + counter; rad > 0; rad--) {
                field[rad] = field[rad - 1];
            }
            for(int j = 0; j <= x_size; j++) {
                field[0][j] = false;
            }

            counter += 1;
        }

        updateClearRows();


    }



}

