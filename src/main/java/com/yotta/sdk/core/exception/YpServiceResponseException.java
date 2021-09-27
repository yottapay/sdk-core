package com.yotta.sdk.core.exception;

import com.yotta.sdk.core.domain.YpServiceResponse;

import java.util.Arrays;

public class YpServiceResponseException extends YpSdkException {
    private final YpServiceResponse serviceResponse;

    public YpServiceResponseException(YpServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public YpServiceResponseException(Throwable cause, YpServiceResponse serviceResponse) {
        super(cause);
        this.serviceResponse = serviceResponse;
    }

    @Override
    public String getMessage() {
        return String.format(
                "Unhandled service response:\n\r\t" +
                        "Status: %d\n\r\t" +
                        "Headers: %s\n\r\t" +
                        "Body: %s",
                serviceResponse.getStatusCode(),
                Arrays.toString(serviceResponse.getHeaders()),
                serviceResponse.getResponseBody()
        );
    }
}
