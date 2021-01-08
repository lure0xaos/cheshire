package alice.cheshire.services;

import alice.cheshire.model.HorizontalAlign;
import alice.cheshire.model.ImageType;
import alice.cheshire.model.Settings;
import alice.cheshire.model.VerticalAlign;
import alice.cheshire.util.Util;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Processor {
    private static final Logger log = LoggerFactory.getLogger(Processor.class);
    private final Settings settings;

    public Processor(Settings settings) {
        this.settings = settings;
    }

    @SneakyThrows
    public Path drawAll(String text) {
        BufferedImage canvasImage = Util.createImage(settings.getImageWidth(), settings.getImageHeight());
        Graphics2D g = Util.getGraphics(canvasImage);
        Rectangle canvasRectangle = new Rectangle(0, 0, settings.getImageWidth(), settings.getImageHeight());
        Util.clearArea(g, settings.getColorBackground(), canvasRectangle);
        String imageSource = settings.getImageSource();
        BufferedImage background = Util.loadImage(Util.getResourcePath(imageSource));
        Rectangle imageRectangle = drawImage(g, canvasRectangle, background);
        drawMainText(text, g, canvasRectangle, imageRectangle);
        drawSignature(g, canvasRectangle, imageRectangle);
        return Util.save(text, canvasImage, imageSource, ImageType.valueOf(settings.getImageType()), settings.getBaseOutputPath());
    }

    private Rectangle drawImage(Graphics2D g, Rectangle canvasRectangle, BufferedImage background) {
        Rectangle sourceRectangle = new Rectangle(background.getWidth(), background.getHeight());
        Rectangle imageRectangle = getImageRectangle(canvasRectangle, sourceRectangle);
        Util.drawImage(g, background, sourceRectangle, imageRectangle);
        return imageRectangle;
    }

    private void drawMainText(String text, Graphics2D g, Rectangle canvasRectangle, Rectangle imageRectangle) {
        String fontLocation = settings.getTextFontLocation();
        Font font = fontLocation == null ? Util.getFont(settings.getTextFontFace(), settings.getTextFontStyle(), settings.getTextFontSize()) : Util.loadFont(Paths.get(fontLocation));
        String[] multi = text.split("\\|");
        Rectangle mainTextRectangle = getMainTextRectangle(canvasRectangle, imageRectangle, settings.getTextFontSize(), settings.getImageAlignHorizontal(), settings.getImageAlignVertical(), multi.length);
        Util.drawText(multi, g, font, settings.getTextFontSize(), settings.getTextColor(), mainTextRectangle, settings.getTextAlignHorizontal(), settings.getTextAlignVertical());
    }

    private Rectangle getMainTextRectangle(Rectangle canvasRectangle, Rectangle imageRectangle, int fontSize, HorizontalAlign imageAlignHorizontal, VerticalAlign imageAlignVertical, int lines) {
        Rectangle rect = new Rectangle(canvasRectangle);
        switch (imageAlignHorizontal) {
            case CENTER:
                rect.x = canvasRectangle.x;
                rect.width = Math.min(canvasRectangle.width, canvasRectangle.width - imageRectangle.width);
                break;
            case LEFT:
                rect.x = canvasRectangle.x + imageRectangle.width;
                rect.width = Math.min(canvasRectangle.width, canvasRectangle.width - imageRectangle.width);
                break;
            case RIGHT:
                rect.x = canvasRectangle.x;
                rect.width = Math.min(canvasRectangle.width, canvasRectangle.width - imageRectangle.width);
                break;
            default:
                throw new IllegalArgumentException("illegal text align");
        }
        int textSize = fontSize * lines;
        switch (imageAlignVertical) {
            case CENTER:
                rect.y = canvasRectangle.y + (canvasRectangle.height - textSize) / 2;
                rect.height = Math.min(canvasRectangle.height, Math.max(textSize, canvasRectangle.height - imageRectangle.height));
                break;
            case TOP:
                rect.y = canvasRectangle.y + imageRectangle.height;
                rect.height = Math.min(canvasRectangle.height, Math.max(textSize, canvasRectangle.height - imageRectangle.height));
                break;
            case BOTTOM:
                rect.y = canvasRectangle.y + canvasRectangle.height - textSize;
                rect.height = Math.min(canvasRectangle.height, Math.max(textSize, canvasRectangle.height - imageRectangle.height));
                break;
            default:
                throw new IllegalArgumentException("illegal text align");
        }
        return rect;
    }

    private Rectangle getImageRectangle(Rectangle canvasRectangle, Rectangle sourceRectangle) {
        return Util.fit(canvasRectangle, sourceRectangle, settings.getImageAlignHorizontal(), settings.getImageAlignVertical());
    }

    private void drawSignature(Graphics2D g, Rectangle canvasRectangle, Rectangle imageRectangle) {
        String fontLocation = settings.getSignatureFontLocation();
        Font font = fontLocation == null ? Util.getFont(settings.getSignatureFontFace(), settings.getSignatureFontStyle(), settings.getSignatureFontSize()) : Util.loadFont(Paths.get(fontLocation));
        Rectangle signatureRectangle = getSignatureRectangle(canvasRectangle, imageRectangle, settings.getSignatureFontSize(), settings.getImageAlignHorizontal(), settings.getImageAlignVertical());
        Util.drawText(new String[]{settings.getSignatureText()}, g, font, settings.getSignatureFontSize(), settings.getSignatureColor(), signatureRectangle, settings.getSignatureAlignHorizontal(), settings.getSignatureAlignVertical());
    }

    private Rectangle getSignatureRectangle(Rectangle canvasRectangle, Rectangle imageRectangle, int fontSize, HorizontalAlign imageAlignHorizontal, VerticalAlign imageAlignVertical) {
        Rectangle rect = new Rectangle(canvasRectangle);
        switch (imageAlignHorizontal) {
            case CENTER:
                rect.x = canvasRectangle.x;
                rect.width = canvasRectangle.width - imageRectangle.width;
                break;
            case LEFT:
                rect.x = canvasRectangle.x + imageRectangle.width;
                rect.width = canvasRectangle.width - imageRectangle.width;
                break;
            case RIGHT:
                rect.x = canvasRectangle.x;
                rect.width = canvasRectangle.width - imageRectangle.width;
                break;
            default:
                throw new IllegalArgumentException("illegal signature align");
        }
        switch (imageAlignVertical) {
            case CENTER:
                rect.y = canvasRectangle.y + (canvasRectangle.height - fontSize);
                rect.height = Math.max(fontSize, canvasRectangle.height - imageRectangle.height);
                break;
            case TOP:
                rect.y = canvasRectangle.y + canvasRectangle.height - fontSize;
                rect.height = Math.max(fontSize, canvasRectangle.height - imageRectangle.height);
                break;
            case BOTTOM:
                rect.y = canvasRectangle.y + imageRectangle.height;
                rect.height = Math.max(fontSize, canvasRectangle.height - imageRectangle.height);
                break;
            default:
                throw new IllegalArgumentException("illegal signature align");
        }
        return rect;
    }
}
