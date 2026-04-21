package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalizationServiceTest {

    private LocalizationService localizationService;
    private Map<String, String> testCache;

    @BeforeEach
    void setUp() throws Exception {
        localizationService = new LocalizationService();
        testCache = new HashMap<>();
        testCache.put("distance.label", "Distance");
        testCache.put("price.label", "Price");

        Field cacheField = LocalizationService.class.getDeclaredField("cache");
        cacheField.setAccessible(true);
        cacheField.set(localizationService, testCache);
    }

    @Test
    void getString_returnsValueWhenKeyExists() {
        assertEquals("Distance", localizationService.getString("distance.label"));
    }

    @Test
    void getString_returnsDefaultTextWhenKeyDoesNotExist() {
        assertEquals("TEXT_NOT_FOUND!", localizationService.getString("missing.key"));
    }

    @Test
    void getAllKeys_returnsAllKeysFromCache() {
        Set<String> keys = localizationService.getAllKeys();

        assertEquals(2, keys.size());
        assertTrue(keys.contains("distance.label"));
        assertTrue(keys.contains("price.label"));
    }

    @Test
    void loadStrings_handlesSQLException_andClearsCache() {
        LocalizationService service = new LocalizationService();

        assertTrue(service.getAllKeys().isEmpty());

        service.loadStrings("en");

        assertTrue(service.getAllKeys().isEmpty());
    }

    @Test
    void loadStrings_clearsExistingCacheBeforeLoading() throws Exception {
        LocalizationService service = new LocalizationService();

        Field cacheField = LocalizationService.class.getDeclaredField("cache");
        cacheField.setAccessible(true);

        Map<String, String> cache = new HashMap<>();
        cache.put("old.key", "old.value");
        cacheField.set(service, cache);

        assertEquals("old.value", service.getString("old.key"));

        service.loadStrings("en");

        assertEquals("TEXT_NOT_FOUND!", service.getString("old.key"));
        assertTrue(service.getAllKeys().isEmpty());
    }

    @Test
    void getAllKeys_returnsEmptySetWhenCacheIsEmpty() throws Exception {
        LocalizationService service = new LocalizationService();

        Field cacheField = LocalizationService.class.getDeclaredField("cache");
        cacheField.setAccessible(true);
        cacheField.set(service, new HashMap<String, String>());

        assertTrue(service.getAllKeys().isEmpty());
    }

    @Test
    void getString_returnsCorrectValueAfterManualCacheReplacement() throws Exception {
        LocalizationService service = new LocalizationService();

        Field cacheField = LocalizationService.class.getDeclaredField("cache");
        cacheField.setAccessible(true);

        Map<String, String> cache = new HashMap<>();
        cache.put("calculate.button", "Calculate");
        cacheField.set(service, cache);

        assertEquals("Calculate", service.getString("calculate.button"));
    }
}