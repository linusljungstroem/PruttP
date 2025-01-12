package kdksd;
import Display.SoundPlayer;

import Pieces.IPiece;
import Pieces.Piece;

import java.util.*;

public class PlayingField {


    private final Integer x_size = 10;
    private final Integer y_size = 20;
    public Square[][] field;
    public Boolean[][] deadField;
    public Piece current_Piece;

    public ArrayList<Piece> piece_queue = new ArrayList<>();

    public ArrayList<Piece> heldPiece = new ArrayList<>();

    public boolean allow_switch = true;

    // Omgjord för att bara testa rotate
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

    public boolean rotate(String rightorleft) {

        if (Objects.equals(rightorleft, "T")) {


            this.erasePiece();
            current_Piece.rotateRight(field);
            this.updatePiece();
            return true;

        } else if (Objects.equals(rightorleft, "K")) {

            this.erasePiece();
            current_Piece.rotateLeft(field);
            this.updatePiece();
            return true;
        }
        else {
            return false;
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

    public void resetField() {
        initField();
    }

    //nya moving metoder, kollar om draget är möjligt, isåfall gör vi draget och skickar sedan true till tetrisframe så
    // att ljudet spelas
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


    public void initQueue() {
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

    // Konstsruktor där vi kan välja vilken form biten ska ha
    public void newPiece(String shape) {

        current_Piece = Piece.createPiece(shape);
        updatePiece();
    }


    public void holdPiece() {
        if (allow_switch) {

            erasePiece(); // Erase the current piece from the field
            current_Piece.resetCoordintas();
            if (!heldPiece.isEmpty()) {
                // Swap the current piece with the held piece
                Piece tempPiece = heldPiece.removeFirst();
                heldPiece.add(current_Piece.copy()); // Add a copy of the current piece to the held piece
                current_Piece = tempPiece.copy(); // Use a copy of the held piece as the current piece
            } else {
                // Store the current piece and generate a new one
                heldPiece.add(current_Piece.copy());
                newPiece(); // Generate a new piece for current_Piece
            }

            allow_switch = false; // Disable switching until the piece lands
            updatePiece(); // Update the field with the new current piece
        }
    }

    // I FLYTTAR
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
    // RETURNS FALSE TRUE
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