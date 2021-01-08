package alice.cheshire.util;

import alice.cheshire.Cheshire;
import alice.cheshire.model.HorizontalAlign;
import alice.cheshire.model.ImageType;
import alice.cheshire.model.VerticalAlign;
import org.intellij.lang.annotations.MagicConstant;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class Util {
    public static final String STYLE_BOLD = "bold";
    public static final String STYLE_BOLD_ITALIC = "bold italic";
    public static final String STYLE_ITALIC = "italic";
    public static final String STYLE_PLAIN = "plain";

    public static void clearArea(Graphics2D g, String background, Rectangle canvasRectangle) {
        g.setColor(parseColor(background));
        g.fillRect(canvasRectangle.x, canvasRectangle.y, canvasRectangle.width, canvasRectangle.height);
    }

    public static Color parseColor(String colorBackground) {
        return new Color(Integer.parseInt(colorBackground.substring(1), 16));
    }

    public static BufferedImage createImage(int imageWidth, int imageHeight) {
        return new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public static void drawImage(Graphics2D g, BufferedImage background, Rectangle sourceRectangle, Rectangle imageRectangle) {
        g.drawImage(background, imageRectangle.x, imageRectangle.y, imageRectangle.width, imageRectangle.height, sourceRectangle.x, sourceRectangle.y, sourceRectangle.width, sourceRectangle.height, null);
    }

    public static void drawText(String[] text, Graphics2D g, String fontFace, String fontStyle, int fontSize, String colorForeground, Rectangle rect, HorizontalAlign alignHorizontal, VerticalAlign alignVertical) {
        Font font = getFont(fontFace, fontStyle, fontSize);
        drawText(text, g, font, fontSize, colorForeground, rect, alignHorizontal, alignVertical);
    }

    public static Font getFont(String fontFace, String fontStyle, int fontSize) {
        return new Font(fontFace, parseFontStyle(fontStyle), fontSize);
    }

    public static void drawText(String[] multi, Graphics2D g, Font font, int fontSize, String colorForeground, Rectangle rect, HorizontalAlign alignHorizontal, VerticalAlign alignVertical) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        Font fnt = font.deriveFont(fontSize);
        Color c = g.getColor();
        g.setColor(c);
        g.setClip(rect.x, rect.y, rect.width, rect.height);
        g.setColor(parseColor(colorForeground));
        g.setFont(fnt);
        FontRenderContext frc = g.getFontRenderContext();
        for (int i = 0; i < multi.length; i++) {
            String text = multi[i];
            LineMetrics lm = fnt.getLineMetrics(text, frc);
            int x;
            switch (alignHorizontal) {
                case CENTER:
                    x = rect.x + (rect.width - (int) fnt.getStringBounds(text, frc).getWidth()) / 2;
                    break;
                case LEFT:
                    x = rect.x + rect.width;
                    break;
                case RIGHT:
                    x = rect.x + (rect.width - (int) fnt.getStringBounds(text, frc).getWidth());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            int y;
            double lineHeight = rect.height / (double) multi.length;
            int delta = (int) ((lineHeight) * i);
            switch (alignVertical) {
                case CENTER:
                    y = (int) (rect.y + delta + (lineHeight + (lm.getAscent() + lm.getDescent())) / 2 - lm.getDescent());
                    break;
                case TOP:
                    y = (int) (rect.y + delta + (lineHeight + (lm.getAscent() + lm.getDescent())) / 2 - lm.getDescent());
                    break;
                case BOTTOM:
                    y = (int) (rect.y + (delta + lineHeight + (lm.getAscent() + lm.getDescent())) / 2 - lm.getDescent());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            g.drawString(text, x, y);
        }
        g.setClip(null);
    }

    @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC})
    public static int parseFontStyle(String style) {
        switch (style) {
            case STYLE_PLAIN:
                return Font.PLAIN;
            case STYLE_BOLD:
                return Font.BOLD;
            case STYLE_ITALIC:
                return Font.ITALIC;
            case STYLE_BOLD_ITALIC:
                return Font.BOLD | Font.ITALIC;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Rectangle fit(Rectangle outer, Rectangle inner, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        Rectangle fit = new Rectangle();
        double w;
        double h;
        double x;
        double y;
        if (inner.width > inner.height) {
            w = outer.width;
            h = w * inner.height / inner.width;
        } else {
            h = outer.height;
            w = h * inner.width / inner.height;
        }
        switch (horizontalAlign) {
            case CENTER:
                x = outer.x + (outer.width - w) / 2;
                break;
            case LEFT:
                x = outer.x;
                break;
            case RIGHT:
                x = outer.x + (outer.width - w);
                break;
            default:
                throw new IllegalArgumentException();
        }
        switch (verticalAlign) {
            case CENTER:
                y = outer.y + (outer.height - h) / 2;
                break;
            case TOP:
                y = outer.y;
                break;
            case BOTTOM:
                y = outer.y + (outer.height - h);
                break;
            default:
                throw new IllegalArgumentException();
        }
        fit.width = (int) w;
        fit.height = (int) h;
        fit.x = (int) x;
        fit.y = (int) y;
        return fit;
    }

    public static Graphics2D getGraphics(BufferedImage canvasImage) {
        return (Graphics2D) canvasImage.getGraphics();
    }

    public static Path getResourcePath(String location) {
        return Paths.get((Objects.requireNonNull(Cheshire.class.getClassLoader().getResource(location), location).toExternalForm().substring("file:/".length())));
    }

    public static Font loadFont(Path location) {
        try (InputStream stream = Files.newInputStream(location)) {
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    public static BufferedImage loadImage(Path resourcePath) throws IOException {
        try (InputStream input = Files.newInputStream(resourcePath)) {
            return ImageIO.read(input);
        }
    }

    public static Path save(String text, BufferedImage canvasImage, String path, ImageType imageType, Path baseOutputPath) throws IOException {
        Path outputPath = baseOutputPath.resolve(getOutputPath(path, Aliases.url(text), imageType.getExtension()));
        if (Files.exists(outputPath)) {
            Files.delete(outputPath);
        }
        try (OutputStream output = Files.newOutputStream(outputPath)) {
            ImageIO.write(canvasImage, imageType.getFormat(), output);
        }
        return outputPath;
    }

    private static Path getOutputPath(String path, String alias, String extension) {
        StringBuilder sb = new StringBuilder();
        sb.append(path, 0, path.lastIndexOf('/') + 1);
        sb.append(path, path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
        sb.append('_');
        sb.append(alias);
        sb.append('.');
        sb.append(extension);
        return Paths.get(sb.toString());
    }
}
