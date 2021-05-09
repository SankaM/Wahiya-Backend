package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.model.*;
import com.monda.edoctor.wahiya.repository.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Setter
public class ManagePatientsService {

    @Autowired
    private BasicPatientInfoRepository basicPatientInfoRepository;

    public List<PatientEntity> getPatientsSummaryOfDoctor(String doctorId){
        return basicPatientInfoRepository.findByDoctorId(doctorId);
    }

    public void registerPatient(PatientEntity patientEntity){
        basicPatientInfoRepository.save(patientEntity);
    }

    public void deletePatient(UUID patientId){
        basicPatientInfoRepository.deleteByPatientId(patientId);
    }
}
