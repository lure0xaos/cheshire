package alice.cheshire.model;

public enum ImageType {
    PNG("image/png", "png", "PNG");
    private final String extension;
    private final String format;
    private final String mime;

    ImageType(String mime, String extension, String format) {
        this.mime = mime;
        this.extension = extension;
        this.format = format;
    }

    public String getExtension() {
        return extension;
    }

    public String getFormat() {
        return format;
    }

    public String getMime() {
        return mime;
    }
}
