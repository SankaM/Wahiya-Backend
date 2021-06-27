package com.monda.edoctor.wahiya.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateContentException extends RuntimeException {
    public DuplicateContentException(String message) {
        super(message);
    }
}
