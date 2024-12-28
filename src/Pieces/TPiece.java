package Pieces;

import java.util.ArrayList;

public class TPiece extends Piece{


    TPiece() {

        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 3});
        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});

        this.pivot = coordinates.get(1);

    }
}
