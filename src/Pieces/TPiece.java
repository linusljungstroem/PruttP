package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class TPiece extends Piece{
    private static final Color color = Color.magenta;


    TPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 3});
        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});

        this.pivot = coordinates.get(1);

    }
}
