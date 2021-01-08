package alice.cheshire.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Texts {
    public static float getMaxFittingFontSize(Graphics g, Font font, String string, int width, int height) {
        int minSize = 0;
        int maxSize = 288;
        int curSize = font.getSize();
        while (maxSize - minSize > 1) {
            FontMetrics fm = g.getFontMetrics(font.deriveFont((float) curSize));
            int fontWidth = fm.stringWidth(string);
            int fontHeight = fm.getLeading() + fm.getMaxAscent() + fm.getMaxDescent();
            if ((fontWidth > width) || (fontHeight > height)) {
                maxSize = curSize;
                curSize = (maxSize + minSize) / 2;
            } else {
                minSize = curSize;
                curSize = (minSize + maxSize) / 2;
            }
        }
        return curSize;
    }
}
