import Pieces.Piece;
import kdksd.Square;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PlayingField {


    private final Integer x_size = 10;
    private final Integer y_size = 20;
    public Square[][] field;
    public Boolean[][] deadField;
    public Piece current_Piece;

    Scanner scan = new Scanner(System.in);


    // Omgjord för att bara testa rotate
    protected PlayingField() {

        initField();
        graphTest();

    }

    private void graphTest() {
        newPiece();

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

    public void rotate(String rightorleft) {

        if (Objects.equals(rightorleft, "T")) {


            this.erasePiece();
            current_Piece.rotateRight(field);
            this.updatePiece();

            System.out.println(current_Piece);

        } else if (Objects.equals(rightorleft, "K")) {

            this.erasePiece();
            current_Piece.rotateLeft(field);
            this.updatePiece();

            System.out.println(current_Piece);
        }
        else {
            return;
        }


    }

    private void initField() {

        field = new Square[y_size][x_size];
        deadField = new Boolean[y_size][x_size];
        for(int i = 0; i < y_size; i++) {
            for(int j = 0; j < x_size; j++) {
                Square sqr = new Square();
                field[i][j] = sqr;
                deadField[i][j] = false;
            }
        }
    }

    public void move(String righorleft) {
        Boolean direction;
        if (Objects.equals(righorleft, "R")) {
            direction = true;
        } else if (Objects.equals(righorleft, "L")) {
            direction = false;
        } else {return;}


        if (checkBounds(direction)) {

            if (direction) {
                this.erasePiece();
                current_Piece.moveRight();
                this.updatePiece();
            }
            else {
                this.erasePiece();
                current_Piece.moveLeft();
                this.updatePiece();
            }
        }
    }

    private boolean checkBounds(Boolean direction) {

        boolean possible = true;

        if (direction) {
            for (int[] i : current_Piece.coordinates){
                int x = i[1];
                int y = i[0];

                if (x == x_size-1) {
                    possible = false;
                    break;
                } else if (deadField[y][x+1]) {
                    possible = false;
                    break;
                }
            }
        }
        else {
            for (int[] i : current_Piece.coordinates){
                int x = i[1];
                int y = i[0];

                if (x == 0) {
                    possible = false;
                    break;
                } else if (deadField[y][x-1]) {
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

                if (field[i][j].isOccupied()) {

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
        updatePiece();

    }

    // Konstsruktor där vi kan välja vilken form biten ska ha
    public void newPiece(String shape) {

        current_Piece = Piece.createPiece(shape);
        updatePiece();
    }

    // I FLYTTAR
    private void updatePiece() {
        for (int[] i : current_Piece.getCoordinates()) {
            int x = i[1];
            int y = i[0];
            System.out.println("Updating square at (" + x + ", " + y + ") with color: " + current_Piece.getColor());
            field[y][x].setOccupied(current_Piece.getColor());
        }
    }


    public void fall() {

        this.erasePiece();
        current_Piece.fall();
        this.updatePiece();

    }

    private void erasePiece() {
        for (int[] i : current_Piece.getCoordinates()) {

            int x = i[1];
            int y = i[0];
            field[y][x].setUnoccupied();
        }
    }
    // RETURNS FALSE TRUE
    public boolean check_kill_Piece() {

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
                deadField[i][j] = field[i][j].isOccupied();
            }
        }
    }

    // Shifts all rows one step downwards, while clearing filled rows
    private void shiftRows(int startRow) {
        for (int row = startRow; row > 0; row--) {
            for (int col = 0; col < x_size; col++) {
                if (field[row - 1][col].isOccupied()) {
                    field[row][col].setOccupied(field[row - 1][col].getColor());
                } else {
                    field[row][col].setUnoccupied();
                }
            }
        }

        // Clear the topmost row after shifting
        for (int col = 0; col < x_size; col++) {
            field[0][col].setUnoccupied();
        }
    }

    public void clearOneRow(int row) {
        for(int i = 0; i < x_size; i++) {
            field[row][i].setUnoccupied();
        }
    }

    private boolean checkFullRow(int row) {

        for(int i = 0; i < x_size; i++) {
            if(!field[row][i].isOccupied()) {
                return false;
            }
        }
        return true;
    }

    public int clearRows() {
        int clearedRows = 0;
        int points = 0;
        for(int row = y_size-1; row >= 0; row--){
            if(checkFullRow(row)) {
                clearOneRow(row);
                shiftRows(row);
                clearedRows ++;
                row++;
            }
        }
        updateClearRows();

        if(clearedRows == 1) {
            points = 10;
        } else if (clearedRows == 2) {
            points = 40;
        } else if (clearedRows == 3) {
            points = 90;
        } else if (clearedRows == 4) {
            points = 160;
        }


        return points;
    }

    public Square[][] getField() {
        return field;
    }


    public boolean gameOver() {

        for(int x = 0; x < x_size; x++){
            if (deadField[0][x]) {
                return true;
            }
        }

        return false;

    }

}

