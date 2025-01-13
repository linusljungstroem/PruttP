package General;

import java.awt.Color;

public class Square {
    private boolean occupied;
    private Color color;

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


}
