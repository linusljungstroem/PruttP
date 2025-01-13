package General;

import Pieces.Piece;

import java.util.*;

public class PlayingField {


    private final Integer x_size = 10;
    private final Integer y_size = 20;
    private Square[][] field;
    private Boolean[][] deadField;
    private Piece current_Piece;

    private ArrayList<Piece> piece_queue = new ArrayList<>();

    private ArrayList<Piece> heldPiece = new ArrayList<>();

    private boolean allow_switch = true;

    public PlayingField() {

        initField();
        initQueue();
        newPiece();

    }

    public ArrayList<Piece> getQueue() {
        return piece_queue;
    }

    public ArrayList<Piece> getHeldPiece() {
        return heldPiece;
    }

    public boolean rotate_right(){


        this.erasePiece();
        boolean possible_right = current_Piece.rotateRight(field);
        this.updatePiece();
        return possible_right;
    }
    public boolean rotate_left(){

        this.erasePiece();
        boolean possible_left = current_Piece.rotateLeft(field);
        this.updatePiece();
        return possible_left;
    }


    public ArrayList<int[]> ghost_coordinates() {
        ArrayList<int[]> ghostCoords = new ArrayList<>();
        for (int[] coord : current_Piece.getCoordinates()) {
            ghostCoords.add(new int[]{coord[0], coord[1]});
        }

        boolean check = true;

        while (check) {
            for (int[] i : ghostCoords) {
                int y = i[0];
                int x = i[1];
                if (y == y_size - 1) {
                    check = false;
                } else if (deadField[y + 1][x]) {
                    check = false;
                }
            }
            if (!check) {
                return ghostCoords;
            } else {
                for (int[] i : ghostCoords) {
                    i[0] += 1;
                }
            }
        }
        return ghostCoords;
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


    public boolean movingleft(){
        if(checkBounds(false)){
            this.erasePiece();
            current_Piece.moveLeft();
            this.updatePiece();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean movingright(){
        if(checkBounds(true)){
            this.erasePiece();
            current_Piece.moveRight();
            this.updatePiece();
            return true;

        }
        else{
            return false;
        }
    }

    private boolean checkBounds(Boolean direction) {

        boolean possible = true;

        if (direction) {
            for (int[] i : current_Piece.getCoordinates()){
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
            for (int[] i : current_Piece.getCoordinates()){
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


    // Terminaltestning
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


    private void initQueue() {
        piece_queue.add(Piece.createPiece());
        piece_queue.add(Piece.createPiece());
        piece_queue.add(Piece.createPiece());
    }


    // Vanliga konstruktorn
    public void newPiece() {
        current_Piece = piece_queue.removeFirst();
        piece_queue.add(Piece.createPiece());
        updatePiece();
    }

    public void holdPiece() {

        if (allow_switch) {

            erasePiece();


            if (!heldPiece.isEmpty()) {
                Piece tempPiece = heldPiece.removeFirst();
                heldPiece.add(current_Piece.copy());
                current_Piece = tempPiece.copy();
            } else {
                heldPiece.add(current_Piece.copy());
                newPiece();
            }
            allow_switch = false;
            updatePiece();
        }
    }


    private void updatePiece() {
        for (int[] i : current_Piece.getCoordinates()) {
            int x = i[1];
            int y = i[0];
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


    public boolean check_kill_Piece() {

        ArrayList<int[]> coordinates = current_Piece.getCoordinates();


        for (int[] i : coordinates) {

            int x = i[1];
            int y = i[0];

            if ( y == y_size-1) {

                updateDeadField(coordinates);
                allow_switch = true;
                return true;

            } else if ( deadField[ y + 1 ][ x ] ) {

                updateDeadField(coordinates);
                allow_switch = true;
                return true;

            }
        }

        return false;
    }

    private void updateDeadField(ArrayList<int[]> coordinates) {

        for(int[] i : coordinates) {

            int x = i[1];
            int y = i[0];
            deadField[y][x] = true;
        }

    }

    private void updateClearRows() {
        for(int i = 0; i < y_size; i++) {
            for (int j = 0; j < x_size; j++) {
                deadField[i][j] = field[i][j].isOccupied();
            }
        }
    }

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

        for (int col = 0; col < x_size; col++) {
            field[0][col].setUnoccupied();
        }
    }

    private void clearOneRow(int row) {
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

        for (int row = 0; row < 2; row++) {

            for (int col = 0; col < x_size; col++) {

                if (deadField[row][col]) {
                    return true;
                }
            }
        }
        return false;



    }

}