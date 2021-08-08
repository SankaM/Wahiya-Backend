package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.req.UpdateDoctorReq;
import com.monda.edoctor.wahiya.dto.req.UpdatePasswordReq;
import com.monda.edoctor.wahiya.dto.res.DoctorRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
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

    public DoctorRes getProfile(UUID doctorId) throws NotFoundException {
        existsById(doctorId);

        return DoctorRes.buildDetail(doctorRepository.getOne(doctorId));
    }

    public DoctorRes updateProfile(UUID doctorId, UpdateDoctorReq req) throws NotFoundException {
        existsById(doctorId);

        var doctor = doctorRepository.getOne(doctorId);
        doctor.setName(req.getName());
        doctor.setDoctorCost(req.getDoctorCost());

        doctorRepository.save(doctor);

        return DoctorRes.buildDetail(doctor);
    }
}
