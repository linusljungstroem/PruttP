package Pieces;

import java.util.ArrayList;

public class ZPiece extends Piece{


    protected ZPiece() {
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 5});
        coordinates.add(new int[]{1, 6});

        this.pivot = coordinates.get(2);
    }
}
