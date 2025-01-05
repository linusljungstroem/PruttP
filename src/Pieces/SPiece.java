package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class SPiece extends Piece{
    private static final Color color = Color.red;


    protected SPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 3});

        this.pivot = coordinates.get(1);
    }
}
