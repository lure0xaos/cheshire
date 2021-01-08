package alice.cheshire.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Maps {
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Object... keysValues) {
        Map<K, V> map = new LinkedHashMap<>();
        for (int i = 0, keysValuesLength = keysValues.length; i < keysValuesLength; i += 2) {
            map.put((K) keysValues[i], (V) keysValues[i + 1]);
        }
        return Collections.unmodifiableMap(map);
    }
}
