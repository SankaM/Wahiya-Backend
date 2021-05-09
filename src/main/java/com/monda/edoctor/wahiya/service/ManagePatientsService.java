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

    private BasicPatientInfoRepository basicPatientInfoRepository;

    @Autowired
    public ManagePatientsService(BasicPatientInfoRepository basicPatientInfoRepository){
        this.basicPatientInfoRepository = basicPatientInfoRepository;
    }

    public List<BasicPatientInfoEntity> getPatientsSummaryOfDoctor(String doctorId){
        return basicPatientInfoRepository.findByDoctorId(doctorId);
    }

    public void registerPatient(BasicPatientInfoEntity basicPatientInfoEntity){
        basicPatientInfoRepository.save(basicPatientInfoEntity);
    }

    public void deletePatient(UUID patientId){
        basicPatientInfoRepository.deleteByPatientId(patientId);
    }
}
