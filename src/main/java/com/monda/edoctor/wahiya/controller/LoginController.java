package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.LoginRequest;
import com.monda.edoctor.wahiya.dto.LoginResponse;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.service.DoctorEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    private DoctorEntityService doctorEntityService;

    @PostMapping(value = "/doctor/login")
    @ResponseStatus(code = HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws LoginException {
        DoctorEntity doctorEntity = doctorEntityService.validateLogin(loginRequest);
        return LoginResponse.builder().doctorId(String.valueOf(doctorEntity.getDoctorId()))
                .doctorName(doctorEntity.getName())
                .userName(loginRequest.getUserName())
                .location(doctorEntity.getAddress3())
                .imageUrl(doctorEntity.getImageLink())
                .build();
    }
}
