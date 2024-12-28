package Pieces;

import java.util.ArrayList;

public class IPiece extends Piece{



    protected IPiece() {

        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{2, 4});
        coordinates.add(new int[]{3, 4});

    }



}
