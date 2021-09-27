package com.yotta.sdk.core.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class YpSdkAbstractConfiguration implements YpSdkConfiguration {

    private final Map<String, Object> properties = new HashMap<>();

    public YpSdkAbstractConfiguration() {
        Class<?>[] utilityClasses = getPropertiesUtilityClasses();
        if (utilityClasses != null) {
            for (Class<?> utilityClass : utilityClasses) {
                YpSdkConfigurationInitializer.initialize(this, utilityClass);
            }
        }
    }

    protected abstract Class<?>[] getPropertiesUtilityClasses();

    @Override
    public @Nullable Object getProperty(@NotNull String key) {
        return properties.get(key);
    }

    @Override
    public <T> void setProperty(@NotNull String key, @Nullable T value) {
        properties.put(key, value);
    }

    @Override
    public boolean isPropertySet(String key) {
        return properties.containsKey(key);
    }

    @Override
    public String toString() {
        String values = properties.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .map(key -> String.format("%s:%s", key, properties.get(key)))
                .collect(Collectors.joining(",\n\r\t"));
        return String.format("%s -- properties: {\n\r\t%s\n\r}", getClass(), values);
    }
}
