package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class TPiece extends Piece{

    private static final Color color = Color.magenta;
    private static final ArrayList<int[]> paintCoordinates;

    static {
        startingCoordinates = new ArrayList<>();
        startingCoordinates.add(new int[]{0, 3});
        startingCoordinates.add(new int[]{0, 4});
        startingCoordinates.add(new int[]{0, 5});
        startingCoordinates.add(new int[]{1, 4});

        paintCoordinates = new ArrayList<>();
        paintCoordinates.add(new int[]{1, 1});
        paintCoordinates.add(new int[]{2, 0});
        paintCoordinates.add(new int[]{2, 1});
        paintCoordinates.add(new int[]{2, 2});
    }

    TPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 3});
        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});

        this.pivot = coordinates.get(1);

    }


    @Override
    public ArrayList<int[]> getPaintCoordinates() {
        return paintCoordinates;
    }

}
