package com.yotta.sdk.core.domain;

import lombok.Data;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
public class YpServiceResponse {
    private final int statusCode;
    private final Header[] headers;
    private final String responseBody;

    public YpServiceResponse(int statusCode, Header[] headers, String responseBody) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    public static YpServiceResponse fromHttpResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            entity.writeTo(baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new YpServiceResponse(
                response.getStatusLine().getStatusCode(),
                response.getAllHeaders(),
                baos.toString()
        );
    }

    public boolean isOk() {
        return statusCode == 200;
    }

    public boolean isForbidden() {
        return statusCode == 403;
    }

    public boolean isNotFound() {
        return statusCode == 404;
    }

    public boolean isInternalServerError() {
        return statusCode == 500;
    }
}