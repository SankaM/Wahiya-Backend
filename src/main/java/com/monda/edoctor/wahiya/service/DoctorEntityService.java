package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.LoginRequest;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorEntityRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Setter
public class DoctorEntityService {

    @Autowired
    private DoctorEntityRepository doctorEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DoctorEntity validateLogin(LoginRequest loginRequest) throws LoginException {
        DoctorEntity doctorEntity = doctorEntityRepository.findByUserName(loginRequest.getUserName()).orElseThrow(LoginException::new);
        if(passwordEncoder.matches(loginRequest.getPassword(), doctorEntity.getPassword())){
            return doctorEntity;
        }
        throw new LoginException();
    }

    public void addDoctor(DoctorEntity doctorEntity) {
        doctorEntity.setPassword(passwordEncoder.encode(doctorEntity.getPassword()));
        doctorEntityRepository.save(doctorEntity);
    }
}
