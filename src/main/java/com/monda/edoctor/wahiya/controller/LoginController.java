package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.req.LoginReq;
import com.monda.edoctor.wahiya.dto.res.LoginRes;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.service.LoginService;
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
    private LoginService loginService;

    @PostMapping(value = "/doctor/login")
    @ResponseStatus(code = HttpStatus.OK)
    public LoginRes login(@RequestBody LoginReq loginReq) throws LoginException {
        DoctorEntity doctorEntity = loginService.validateDoctorLogin(loginReq);
        return LoginRes.build(doctorEntity);
    }
}
