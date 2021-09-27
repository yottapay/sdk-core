package com.yotta.sdk.core.config;

import com.yotta.sdk.core.property.YpProperty;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public interface YpSdkConfigurationInitializer {
    static void initialize(@NotNull YpSdkConfiguration target, @NotNull Class<?> propertyUtilityClass) {
        Field[] fields = propertyUtilityClass.getFields();

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (!field.getType().isInstance(YpProperty.class)) {
                continue;
            }

            Object staticValue;
            try {
                staticValue = field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (staticValue instanceof YpProperty) {
                YpProperty<?> property = (YpProperty<?>) staticValue;
                String propertyKey = property.getKey();

                if (target.isPropertySet(propertyKey)) {
                    throw new RuntimeException(String.format("Target map already contains that property: %s", propertyKey));
                }

                property.setDefault(target);
            }
        }
    }
}
