package Pieces;

import General.Square;
import java.awt.*;
import java.util.ArrayList;

public class OPiece extends Piece{
    private static final Color color = Color.yellow;
    private static final ArrayList<int[]> paintCoordinates;

    static {
        startingCoordinates = new ArrayList<>();
        startingCoordinates.add(new int[]{0, 4});
        startingCoordinates.add(new int[]{0, 5});
        startingCoordinates.add(new int[]{1, 4});
        startingCoordinates.add(new int[]{1, 5});

        paintCoordinates = new ArrayList<>();
        paintCoordinates.add(new int[]{1, 1});
        paintCoordinates.add(new int[]{1, 2});
        paintCoordinates.add(new int[]{2, 1});
        paintCoordinates.add(new int[]{2, 2});

    }

    OPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{1, 5});

        this.pivot = coordinates.get(1);
    }


    @Override
    public ArrayList<int[]> getPaintCoordinates() {
        return paintCoordinates;
    }

    // O-blocks don't rotate
    @Override
    public boolean rotateRight(Square[][] playingField) {return false;}

    @Override
    public boolean rotateLeft(Square[][] playingField) {return false;}


}
