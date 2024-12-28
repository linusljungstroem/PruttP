package Pieces;

import java.util.ArrayList;

public class LPiece extends Piece{

    protected LPiece() {
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{2, 4});
        coordinates.add(new int[]{2, 5});
    }

}
