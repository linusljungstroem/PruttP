package kdksd;

import java.awt.Color;

public class Square {
    private boolean occupied; // True if the square is occupied
    private Color color;      // The color of the occupying piece (null if unoccupied)

    public Square() {
        this.occupied = false;
        this.color = Color.lightGray; // Unoccupied squares are grey
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(Color color) {
        this.occupied = true;
        this.color = color;
    }

    public void setUnoccupied() {
        this.occupied = false;
        this.color = null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void clear() {
        this.occupied = false;
        this.color = null;
    }
}
