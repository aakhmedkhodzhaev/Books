package com.onevision.books.utils;

import com.onevision.books.dto.wrappers.RestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

    public <T> ResponseEntity<T> createResponse(T object) {
        HttpStatus httpStatus = HttpStatus.OK;
        if (object instanceof RestResponse) {
            RestResponse restResponse = (RestResponse) object;
            if (restResponse.getError() != 0)
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return createResponse(object, httpStatus);
    }

    public <T> ResponseEntity<T> createResponse(T object, HttpStatus status) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new ResponseEntity<>(object, responseHeaders, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
