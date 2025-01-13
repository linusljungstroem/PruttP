package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class JPiece extends Piece{

    private static final Color color = Color.pink;
    private static final ArrayList<int[]> paintCoordinates;

    static {
        startingCoordinates = new ArrayList<>();
        startingCoordinates.add(new int[]{0, 4});
        startingCoordinates.add(new int[]{1, 4});
        startingCoordinates.add(new int[]{2, 4});
        startingCoordinates.add(new int[]{2, 3});

        paintCoordinates = new ArrayList<>();
        paintCoordinates.add(new int[]{0, 3});
        paintCoordinates.add(new int[]{1, 3});
        paintCoordinates.add(new int[]{2, 3});
        paintCoordinates.add(new int[]{2, 2});

    }

    protected JPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{2, 4});
        coordinates.add(new int[]{2, 3});

        this.pivot = coordinates.get(1);
    }

    @Override
    public ArrayList<int[]> getPaintCoordinates() {
        return paintCoordinates;
    }

}
