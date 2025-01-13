package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class ZPiece extends Piece{

    private static final Color color = Color.green;
    private static final ArrayList<int[]> paintCoordinates;

    static {
        startingCoordinates = new ArrayList<>();
        startingCoordinates.add(new int[]{0, 4});
        startingCoordinates.add(new int[]{1, 5});
        startingCoordinates.add(new int[]{0, 5});
        startingCoordinates.add(new int[]{1, 6});

        paintCoordinates = new ArrayList<>();
        paintCoordinates.add(new int[]{1, 0});
        paintCoordinates.add(new int[]{1, 1});
        paintCoordinates.add(new int[]{2, 1});
        paintCoordinates.add(new int[]{2, 2});

    }

    protected ZPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 5});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 6});

        this.pivot = coordinates.get(1);
    }


    @Override
    public ArrayList<int[]> getPaintCoordinates() {
        return paintCoordinates;
    }

}
