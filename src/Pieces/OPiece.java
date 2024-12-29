package Pieces;

import java.util.ArrayList;

public class OPiece extends Piece{


    protected OPiece() {
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{1, 5});


        this.pivot = coordinates.get(1);
    }

    // O-blocks don't rotate
    @Override
    public void rotateRight(boolean[][] playingField) {}

    @Override
    public void rotateLeft(boolean[][] playingField) {}


}
