package com.monda.edoctor.wahiya.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class WrongParameterException extends Exception {
    public WrongParameterException(String message) {
        super(message);
    }
}
