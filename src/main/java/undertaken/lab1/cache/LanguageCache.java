package undertaken.lab1.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LanguageCache {
    private static final int MAX_CACHE_SIZE = 3;

    private final Map<String, String> cache = new LinkedHashMap<String, String>(MAX_CACHE_SIZE, 0.75f, true) { //коэф загрузки
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    private final Logger logger = LoggerFactory.getLogger(LanguageCache.class);

    public synchronized String get(String text) {
        return cache.get(text);
    }

    public synchronized void put(String text, String language) { //синхронизирован(только один поток может выполнять эту функцию)
        cache.put(text, language);
    }

    public void printCache() {
        logger.info("Cache content: ");
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            logger.info("Text: {}, Language {} ", entry.getKey(), entry.getValue());
        }
    }
}
