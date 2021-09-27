package com.yotta.sdk.core.property;

public class YpStringProperty extends YpProperty<String> {

    public YpStringProperty(String key, boolean required, String defaultValue) {
        super(String.class, key, required, defaultValue);
    }

    public YpStringProperty(String key, boolean required) {
        super(String.class, key, required);
    }

    public YpStringProperty(String key, String defaultValue) {
        super(String.class, key, defaultValue);
    }

    public YpStringProperty(String key) {
        super(String.class, key);
    }
}
