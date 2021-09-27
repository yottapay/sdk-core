package com.yotta.sdk.core.exception;

public class YpSdkException extends RuntimeException {

    public YpSdkException() {
    }

    public YpSdkException(String message) {
        super(message);
    }

    public YpSdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public YpSdkException(Throwable cause) {
        super(cause);
    }

    public YpSdkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
