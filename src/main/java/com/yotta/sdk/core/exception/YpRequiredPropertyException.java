package com.yotta.sdk.core.exception;

import com.yotta.sdk.core.property.YpProperty;

public class YpRequiredPropertyException extends YpSdkException {
    private static final String DEFAULT_MESSAGE = "Required property is null";
    private static final String PROPERTY_KEY_MESSAGE_FORMAT = "Required property \"%s\" is null";
    private final String message;

    public YpRequiredPropertyException(YpProperty<?> property) {
        this.message = property != null
                ? String.format(PROPERTY_KEY_MESSAGE_FORMAT, property.getKey())
                : DEFAULT_MESSAGE;
    }

    public YpRequiredPropertyException(String message) {
        this.message = (message != null && !message.isEmpty())
                ? message
                : DEFAULT_MESSAGE;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
