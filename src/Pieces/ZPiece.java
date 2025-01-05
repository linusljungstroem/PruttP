package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class ZPiece extends Piece{
    private static final Color color = Color.green;


    protected ZPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 5});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 6});

        this.pivot = coordinates.get(1);
    }
}
