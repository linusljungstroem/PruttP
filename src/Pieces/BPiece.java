package Pieces;

import java.util.ArrayList;

public class BPiece extends Piece{


    protected BPiece() {
        coordinates = new ArrayList<>();

        coordinates.add(new int[]{0, 4});
        coordinates.add(new int[]{0, 5});
        coordinates.add(new int[]{1, 4});
        coordinates.add(new int[]{1, 5});


        this.pivot = coordinates.get(1);
    }




}
