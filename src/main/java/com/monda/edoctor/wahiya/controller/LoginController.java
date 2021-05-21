package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.LoginRequest;
import com.monda.edoctor.wahiya.dto.LoginResponse;
import com.monda.edoctor.wahiya.dto.PatientSummary;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.service.ManagePatientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class LoginController {

    @PostMapping(value = "/doctor/login")
    @ResponseStatus(code = HttpStatus.OK)
    public LoginResponse getPatientsSummaryOfDoctor(@RequestBody LoginRequest loginRequest) throws LoginException {
        if(!(loginRequest.getUserName().equals("test1") &&
                loginRequest.getPassword().equals("password1"))){
            throw new LoginException();
        }
        return LoginResponse.builder().doctorId("EDR1")
                .doctorName("Dr.David")
                .userName(loginRequest.getUserName())
                .build();
    }
}
