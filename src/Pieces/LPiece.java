package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class LPiece extends Piece{

    private static final Color color = Color.orange;

    protected LPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{2, 4});
        coordinates.add(new int[]{2, 5});

        this.pivot = coordinates.get(1);
    }

}
