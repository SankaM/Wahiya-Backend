package com.monda.edoctor.wahiya.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Doctor ID not available")
public class DoctorNotFoundException extends Exception{

}
