package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.req.LoginReq;
import com.monda.edoctor.wahiya.dto.req.UpdatePasswordReq;
import com.monda.edoctor.wahiya.dto.res.LoginRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/doctor/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<LoginRes> login(@RequestBody LoginReq loginReq) throws LoginException {
        DoctorEntity doctorEntity = accountService.validateDoctorLogin(loginReq);
        return new ResponseWrapper<>(true, null, LoginRes.buildDetail(doctorEntity));
    }

    @PutMapping(value = "/doctors/{doctorId}/update-password")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper updatePassword(@PathVariable("doctorId") UUID doctorId,
                                          @RequestBody UpdatePasswordReq req) throws NotFoundException, WrongParameterException {

        accountService.updatePassword(doctorId, req);

        return new ResponseWrapper(true, null, null);
    }
}
