package util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class Utility {
    public static void addPadding(JComponent component) {
        Border border = component.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        component.setBorder(new CompoundBorder(border, margin));
    }

    public static Font getLargeFont() {
        return new Font("Courier", Font.BOLD, 25);
    }

    public static Font getMediumFont() {
        return new Font("Courier", Font.BOLD, 18);
    }

    public static Font getSmallFont() {
        return new Font("Courier", Font.BOLD, 12);
    }
}
