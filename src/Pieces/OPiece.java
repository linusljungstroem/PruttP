package Pieces;

import kdksd.Square;
import java.awt.*;
import java.util.ArrayList;

public class OPiece extends Piece{
    private static final Color color = Color.yellow;


    protected OPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{1, 5});


        this.pivot = coordinates.get(1);
    }

    // O-blocks don't rotate
    @Override
    public void rotateRight(Square[][] playingField) {}

    @Override
    public void rotateLeft(Square[][] playingField) {}


}
