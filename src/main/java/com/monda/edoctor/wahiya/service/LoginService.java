package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.req.LoginReq;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DoctorEntity validateDoctorLogin(LoginReq loginReq) throws LoginException {
        DoctorEntity doctorEntity = doctorRepository.findByUserName(loginReq.getUserName()).orElseThrow(LoginException::new);
        if (passwordEncoder.matches(loginReq.getPassword(), doctorEntity.getPassword())) {
            return doctorEntity;
        }

        log.debug("Password does not matched :" + doctorEntity.getUserName());
        throw new LoginException();
    }
}
