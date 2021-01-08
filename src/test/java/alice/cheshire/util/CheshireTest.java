package alice.cheshire.util;

import alice.cheshire.model.HorizontalAlign;
import alice.cheshire.model.VerticalAlign;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheshireTest {
    @Test
    void fit1() {
        Rectangle fit = Util.fit(new Rectangle(0, 0, 400, 400), new Rectangle(0, 0, 1600, 800), HorizontalAlign.CENTER, VerticalAlign.CENTER);
        assertEquals(new Rectangle(0, 100, 400, 200), fit);
    }

    @Test
    void fit2() {
        Rectangle fit = Util.fit(new Rectangle(0, 0, 400, 400), new Rectangle(0, 0, 800, 1600), HorizontalAlign.CENTER, VerticalAlign.CENTER);
        assertEquals(new Rectangle(100, 0, 200, 400), fit);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}
