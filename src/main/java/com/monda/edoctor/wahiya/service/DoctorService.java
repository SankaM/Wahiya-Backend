package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import com.monda.edoctor.wahiya.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public boolean existsById(UUID id) throws NotFoundException {
        if (!doctorRepository.existsById(id)) {
            log.error("Doctor ID not available : {}", id);
            throw new NotFoundException("Doctor ID not available");
        }

        return true;
    }
}
