package Pieces;

import java.awt.*;
import java.util.ArrayList;

public class JPiece extends Piece{

    private static final Color color = Color.pink;


    protected JPiece() {
        super(color);
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{2, 4});
        coordinates.add(new int[]{2, 3});

        this.pivot = coordinates.get(1);
    }
}
