package alice.cheshire.util;

import org.junit.jupiter.api.Test;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TextsTest {
    @Test
    void getMaxFittingFontSize() {
        subText(100, 40);
        subText(100, 400);
        subText(10, 40);
    }

    private void subText(int width, int height) {
        Graphics g = GraphicsEnvironment.getLocalGraphicsEnvironment().createGraphics(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        Font font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        String text = "Wg";
        float maxFittingFontSize = Texts.getMaxFittingFontSize(g, font, text, width, height);
        g.setFont(font.deriveFont(maxFittingFontSize));
        Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(text, g);
        assertTrue(stringBounds.getWidth() <= width, "width: " + stringBounds.getWidth() + " < " + width);
        assertTrue(stringBounds.getHeight() <= height, "height: " + stringBounds.getHeight() + "<" + height);
    }
}
