/*package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;



public class CustomErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        boolean hasError = false;
        int rawStatusCode = response.getRawStatusCode();
        if (rawStatusCode != 200){
            hasError = true;
        }
        return hasError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new CustomHttpException(response.getStatusCode());
    }
}*/

