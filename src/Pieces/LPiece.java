package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class LPiece extends Piece{

    private static final Color color = Color.orange;
    private static final ArrayList<int[]> paintCoordinates;
    static {
        startingCoordinates = new ArrayList<>();
        startingCoordinates.add(new int[]{0, 4});
        startingCoordinates.add(new int[]{1, 4});
        startingCoordinates.add(new int[]{2, 4});
        startingCoordinates.add(new int[]{2, 5});

        paintCoordinates = new ArrayList<>();
        paintCoordinates.add(new int[]{0, 1});
        paintCoordinates.add(new int[]{1, 1});
        paintCoordinates.add(new int[]{2, 1});
        paintCoordinates.add(new int[]{2, 2});

    }

    protected LPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{2, 4});
        coordinates.add(new int[]{2, 5});

        this.pivot = coordinates.get(1);
    }


    @Override
    public ArrayList<int[]> getPaintCoordinates() {
        return paintCoordinates;
    }

}
