package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.LoginRequest;
import com.monda.edoctor.wahiya.exception.DoctorNotFoundException;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorEntityRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.UUID;

@Service
@Setter
public class DoctorEntityService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorEntityService.class);

    @Autowired
    private DoctorEntityRepository doctorEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsById(UUID id) throws DoctorNotFoundException {
        if(!doctorEntityRepository.existsById(id)){
            logger.error("ID not available : {}", id);
            throw new DoctorNotFoundException();
        }
        return true;
    }

    public DoctorEntity validateLogin(LoginRequest loginRequest) throws LoginException {
        DoctorEntity doctorEntity = doctorEntityRepository.findByUserName(loginRequest.getUserName()).orElseThrow(LoginException::new);
        if(passwordEncoder.matches(loginRequest.getPassword(), doctorEntity.getPassword())){
            return doctorEntity;
        }
        logger.debug("Password does not matched :" + doctorEntity.getUserName());
        throw new LoginException();
    }

    public void addDoctor(DoctorEntity doctorEntity) {
        //For dummy purpose doctor id randomly generated
        UUID uuid = UUID.randomUUID();
        if(doctorEntityRepository.findById(uuid).isPresent()){
            logger.error("ID Already Available :" + doctorEntity.getDoctorId());
            addDoctor(doctorEntity);
        }else{
            doctorEntity.setDoctorId(uuid);
            doctorEntity.setPassword(passwordEncoder.encode(doctorEntity.getPassword()));
            doctorEntityRepository.save(doctorEntity);
            logger.debug("Doctor Added Successfully : ID : " + doctorEntity.getDoctorId()  + " Name : " + doctorEntity.getName());
        }
    }
}
