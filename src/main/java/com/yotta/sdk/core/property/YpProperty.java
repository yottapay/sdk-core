package com.yotta.sdk.core.property;

import com.yotta.sdk.core.config.YpSdkConfiguration;
import com.yotta.sdk.core.exception.YpRequiredPropertyException;

public class YpProperty<T> {
    private final Class<T> type;
    private final String key;
    private final boolean required;
    private final T defaultValue;

    public YpProperty(Class<T> type, String key, boolean required, T defaultValue) {
        this.type = type;
        this.key = key;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    public YpProperty(Class<T> type, String key, boolean required) {
        this(type, key, required, null);
    }

    public YpProperty(Class<T> type, String key, T defaultValue) {
        this(type, key, false, defaultValue);
    }

    public YpProperty(Class<T> type, String key) {
        this(type, key, false, null);
    }

    public T get(YpSdkConfiguration properties) throws YpRequiredPropertyException {

        Object value;
        String key = getKey();
        if (!properties.isPropertySet(key)) {
            T defaultValue = getDefaultValue();
            properties.setProperty(key, defaultValue);
            value = defaultValue;
        } else {
            value = properties.getProperty(key);
        }

        if (value == null) {

            if (isRequired()) {
                throw new YpRequiredPropertyException(this);
            }

            return null;
        }

        return getType().cast(value);
    }

    public void set(YpSdkConfiguration properties, T value) {
        properties.setProperty(getKey(), value);
    }

    public void setDefault(YpSdkConfiguration properties) {
        properties.setProperty(getKey(), getDefaultValue());
    }

    public Class<T> getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean isRequired() {
        return required;
    }
}