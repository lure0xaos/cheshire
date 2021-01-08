package alice.cheshire.model;

import java.nio.charset.Charset;
import java.nio.file.Path;

public class Settings {
    private Charset charset;
    private String colorBackground;
    private HorizontalAlign imageAlignHorizontal;
    private VerticalAlign imageAlignVertical;
    private int imageHeight;
    private String imageSource;
    private String imageType;
    private int imageWidth;
    private HorizontalAlign signatureAlignHorizontal;
    private VerticalAlign signatureAlignVertical;
    private String signatureColor;
    private String signatureFontFace;
    private String signatureFontLocation;
    private int signatureFontSize;
    private String signatureFontStyle;
    private String signatureText;
    private HorizontalAlign textAlignHorizontal;
    private VerticalAlign textAlignVertical;
    private String textColor;
    private String textFontFace;
    private String textFontLocation;
    private int textFontSize;
    private String textFontStyle;
    private String textSource;
    private Path baseOutputPath;

    public Settings() {
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public HorizontalAlign getImageAlignHorizontal() {
        return imageAlignHorizontal;
    }

    public void setImageAlignHorizontal(HorizontalAlign imageAlignHorizontal) {
        this.imageAlignHorizontal = imageAlignHorizontal;
    }

    public VerticalAlign getImageAlignVertical() {
        return imageAlignVertical;
    }

    public void setImageAlignVertical(VerticalAlign imageAlignVertical) {
        this.imageAlignVertical = imageAlignVertical;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public HorizontalAlign getSignatureAlignHorizontal() {
        return signatureAlignHorizontal;
    }

    public void setSignatureAlignHorizontal(HorizontalAlign signatureAlignHorizontal) {
        this.signatureAlignHorizontal = signatureAlignHorizontal;
    }

    public VerticalAlign getSignatureAlignVertical() {
        return signatureAlignVertical;
    }

    public void setSignatureAlignVertical(VerticalAlign signatureAlignVertical) {
        this.signatureAlignVertical = signatureAlignVertical;
    }

    public String getSignatureColor() {
        return signatureColor;
    }

    public void setSignatureColor(String signatureColor) {
        this.signatureColor = signatureColor;
    }

    public String getSignatureFontFace() {
        return signatureFontFace;
    }

    public void setSignatureFontFace(String signatureFontFace) {
        this.signatureFontFace = signatureFontFace;
    }

    public String getSignatureFontLocation() {
        return signatureFontLocation;
    }

    public void setSignatureFontLocation(String signatureFontLocation) {
        this.signatureFontLocation = signatureFontLocation;
    }

    public int getSignatureFontSize() {
        return signatureFontSize;
    }

    public void setSignatureFontSize(int signatureFontSize) {
        this.signatureFontSize = signatureFontSize;
    }

    public String getSignatureFontStyle() {
        return signatureFontStyle;
    }

    public void setSignatureFontStyle(String signatureFontStyle) {
        this.signatureFontStyle = signatureFontStyle;
    }

    public String getSignatureText() {
        return signatureText;
    }

    public void setSignatureText(String signatureText) {
        this.signatureText = signatureText;
    }

    public HorizontalAlign getTextAlignHorizontal() {
        return textAlignHorizontal;
    }

    public void setTextAlignHorizontal(HorizontalAlign textAlignHorizontal) {
        this.textAlignHorizontal = textAlignHorizontal;
    }

    public VerticalAlign getTextAlignVertical() {
        return textAlignVertical;
    }

    public void setTextAlignVertical(VerticalAlign textAlignVertical) {
        this.textAlignVertical = textAlignVertical;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextFontFace() {
        return textFontFace;
    }

    public void setTextFontFace(String textFontFace) {
        this.textFontFace = textFontFace;
    }

    public String getTextFontLocation() {
        return textFontLocation;
    }

    public void setTextFontLocation(String textFontLocation) {
        this.textFontLocation = textFontLocation;
    }

    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }

    public String getTextFontStyle() {
        return textFontStyle;
    }

    public void setTextFontStyle(String textFontStyle) {
        this.textFontStyle = textFontStyle;
    }

    public String getTextSource() {
        return textSource;
    }

    public void setTextSource(String textSource) {
        this.textSource = textSource;
    }

    public Path getBaseOutputPath() {
        return baseOutputPath;
    }

    public void setBaseOutputPath(Path baseOutputPath) {
        this.baseOutputPath = baseOutputPath;
    }
}
