package Display;

import javax.swing.*;
import java.awt.*;

public abstract class ButtonDisplay extends JPanel {

    JLabel label;
    JButton b1;
    JButton b2;
    JButton b3;

    static GridLayout layout = new GridLayout(4, 1, 10, 10);
    static Font font = new Font("Arial", Font.BOLD, 20);

    TetrisFrame frame;
}
