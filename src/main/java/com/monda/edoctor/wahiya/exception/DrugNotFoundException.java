package com.monda.edoctor.wahiya.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Drug not available")
public class DrugNotFoundException extends Exception {

}
