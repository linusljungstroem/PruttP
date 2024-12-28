package Pieces;

import java.util.ArrayList;

public class SPiece extends Piece{


    protected SPiece() {
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{1, 3});

        this.pivot = coordinates.get(2);
    }
}
