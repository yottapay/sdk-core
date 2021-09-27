package com.yotta.sdk.core.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface YpSdkConfiguration {

    String GLOBAL_PREFIX = propertyPath("com", "yotta", "sdk");

    static String propertyPath(String... parts) {
        return String.join(".", parts);
    }

    @Nullable
    Object getProperty(@NotNull String key);

    <T> void setProperty(@NotNull String key, @Nullable T value);

    boolean isPropertySet(String key);
}
