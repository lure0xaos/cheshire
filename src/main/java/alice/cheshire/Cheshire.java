package alice.cheshire;

import alice.cheshire.model.HorizontalAlign;
import alice.cheshire.model.Settings;
import alice.cheshire.model.VerticalAlign;
import alice.cheshire.services.Processor;
import alice.cheshire.services.Vk;
import alice.cheshire.util.Util;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.awt.Desktop;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cheshire {
    public static final boolean SEND = false;
    private final Logger log = org.slf4j.LoggerFactory.getLogger(Cheshire.class);

    public Cheshire() {
    }

    public static void main(String[] args) {
        Settings settings = getDefaultSettings();
        new Cheshire().start(settings);
    }

    public static Settings getDefaultSettings() {
        Settings settings = new Settings();
        settings.setCharset(StandardCharsets.UTF_8);
        settings.setColorBackground("#000000");
        settings.setImageAlignHorizontal(HorizontalAlign.LEFT);
        settings.setImageAlignVertical(VerticalAlign.CENTER);
        settings.setImageHeight(400);
        settings.setImageSource("alice-madness-returns-cheshire-cat.jpg");
        settings.setImageType("PNG");
        settings.setImageWidth(800);
        settings.setTextColor("#FFFFFF");
        settings.setTextFontFace("Arial");
        settings.setTextFontSize(20);
        settings.setTextFontStyle("plain");
        settings.setTextSource("text.txt");
        settings.setTextAlignHorizontal(HorizontalAlign.CENTER);
        settings.setTextAlignVertical(VerticalAlign.CENTER);
        settings.setSignatureColor("#FF33FF");
        settings.setSignatureFontFace("Arial");
        settings.setSignatureFontSize(10);
        settings.setSignatureFontStyle("plain");
        settings.setSignatureText("Улыбка Чеширского кота");
        settings.setSignatureAlignHorizontal(HorizontalAlign.RIGHT);
        settings.setSignatureAlignVertical(VerticalAlign.BOTTOM);
        settings.setBaseOutputPath(Paths.get(System.getProperty("user.home", ".")));
        return settings;
    }

    @SneakyThrows
    private void start(Settings settings) {
        Processor processor = new Processor(settings);
        Path path = Util.getResourcePath(settings.getTextSource());
        log.info("reading texts from " + path);
        int i = 0;
        for (String text : Files.readAllLines(path, settings.getCharset())) {
            if (text.trim().isEmpty()) break;
            log.info(++i + ": " + text);
            Path resultPath = processor.drawAll(text);
            log.info("result: " + resultPath);
            if (SEND) {
                new Vk().send(resultPath);
            } else {
                Desktop.getDesktop().open(resultPath.toFile());
            }
        }
    }
}
