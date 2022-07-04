package com.mycompany.pmt4;
import javax.swing.*;
import java.awt.*;

public class DancingTextPanel extends JPanel {

    private static final Color[] Palette
            = {
                Color.black,
                Color.blue,
                Color.cyan,
                Color.darkGray,
                Color.gray,
                Color.green,
                Color.magenta,
                Color.orange,
                Color.pink,
                Color.red,
                Color.white};

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Erst mal die super-Methode aufrufen.

        //Implementation of Tanzende Schrift Example by [Jobst06; p.234]
        char[] chText = {'T', 'a', 'n', 'z', 'e', 'n', 'd', 'e', ' ', 'S', 'c', 'h', 'r', 'i', 'f', 't'};

        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        FontMetrics m = g.getFontMetrics();
        int height = m.getHeight() + 5;
        int width = m.charWidth(chText[0]);

        //Correct width, if a bigger char exists:
        for (int i = 1; i < chText.length; i++) {
            if (m.charWidth(chText[i]) > width) {
                width = m.charWidth(chText[i]);
            }
        }

        for (int j = 0; j < chText.length; j++) {
            int start = (int) Math.round(20 * Math.random());
            g.setColor(Palette[start % Palette.length]);
            g.drawChars(chText, j, 1, j * width, height + start);
        }
    }

}
