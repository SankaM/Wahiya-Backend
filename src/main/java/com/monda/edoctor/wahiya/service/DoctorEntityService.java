package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.DoctorResponse;
import com.monda.edoctor.wahiya.dto.LoginRequest;
import com.monda.edoctor.wahiya.exception.DuplicateContentException;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorEntityRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Setter
public class DoctorEntityService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorEntityService.class);

    @Autowired
    private DoctorEntityRepository doctorEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsById(UUID id) throws NotFoundException {
        if (!doctorEntityRepository.existsById(id)) {
            logger.error("ID not available : {}", id);
            throw new NotFoundException("Requested doctor ID not available");
        }
        return true;
    }

    public DoctorEntity save(DoctorEntity doctorEntity) {
        try {
            return doctorEntityRepository.saveAndFlush(doctorEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate Record : {}", e.getMessage());
            throw new DuplicateContentException(e.getMessage());
        }
    }

    public DoctorResponse getDoctorResponse(UUID id) {
        DoctorEntity doctorEntity = doctorEntityRepository.findById(id).get();
        return DoctorResponse.builder().id(doctorEntity.getId().toString()).imageURL(doctorEntity.getImageLink()).name(doctorEntity.getName()).build();
    }

    public DoctorEntity validateLogin(LoginRequest loginRequest) throws LoginException {
        DoctorEntity doctorEntity = doctorEntityRepository.findByUserName(loginRequest.getUserName()).orElseThrow(LoginException::new);
        if (passwordEncoder.matches(loginRequest.getPassword(), doctorEntity.getPassword())) {
            return doctorEntity;
        }
        logger.debug("Password does not matched :" + doctorEntity.getUserName());
        throw new LoginException();
    }

    public void addDoctor(DoctorEntity doctorEntity) {
        //For dummy purpose doctor id randomly generated
        UUID uuid = UUID.randomUUID();
        if (doctorEntityRepository.findById(uuid).isPresent()) {
            logger.error("ID Already Available :" + doctorEntity.getId());
            addDoctor(doctorEntity);
        } else {
            doctorEntity.setId(uuid);
            doctorEntity.setPassword(passwordEncoder.encode(doctorEntity.getPassword()));
            save(doctorEntity);
            logger.debug("Doctor Added Successfully : ID : " + doctorEntity.getId() + " Name : " + doctorEntity.getName());
        }
    }
}
