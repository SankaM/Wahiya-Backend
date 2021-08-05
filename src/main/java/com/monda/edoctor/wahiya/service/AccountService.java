package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.req.LoginReq;
import com.monda.edoctor.wahiya.dto.req.UpdatePasswordReq;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AccountService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;

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

    public void updatePassword(UUID doctorId, UpdatePasswordReq req) throws NotFoundException, WrongParameterException {
        doctorService.existsById(doctorId);

        DoctorEntity doctor = doctorRepository.getOne(doctorId);
        if (!passwordEncoder.matches(req.getOldPassword(), doctor.getPassword())) {
            throw new WrongParameterException("Wrong old password");
        }

        doctor.setPassword(passwordEncoder.encode(req.getNewPassword()));
        doctorRepository.save(doctor);
    }
}
