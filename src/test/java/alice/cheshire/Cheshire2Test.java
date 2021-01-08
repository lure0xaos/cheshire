package alice.cheshire;

import alice.cheshire.model.Settings;
import org.junit.jupiter.api.BeforeEach;

class Cheshire2Test {
    private Settings settings;

    @BeforeEach
    void setUp() {
        settings = Cheshire.getDefaultSettings();
    }
}
