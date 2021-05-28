package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.PatientSummary;
import com.monda.edoctor.wahiya.dto.RegisterPatientRequest;
import com.monda.edoctor.wahiya.model.*;
import com.monda.edoctor.wahiya.repository.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Setter
public class ManagePatientsService {

    @Autowired
    private PatientEntityRepository patientEntityRepository;

    public List<PatientSummary> getPatientsSummaryOfDoctor(String doctorId){
        List<PatientEntity> patients = patientEntityRepository.findByDoctorIdAndIsActive(doctorId, true);
        return patients.stream().map(p-> PatientSummary.builder()
                .age(p.getAge()).name(p.getName()).mobile(p.getMobile())
                .patientId(p.getPatientId())
                .imageUrl("https://wahiya-edoctor.s3.us-east-2.amazonaws.com/patients/photo-patient.jpeg")
                .build()).collect(Collectors.toList());
    }

    public void registerPatient(RegisterPatientRequest registerPatientRequest, String doctorId){
        patientEntityRepository.save(PatientEntity.builder().age(registerPatientRequest.getAge())
                .birthDate(registerPatientRequest.getBirthDate()).mobile(registerPatientRequest.getMobile())
                .email(registerPatientRequest.getEmail()).name(registerPatientRequest.getName())
                .healthProfile(registerPatientRequest.getHealthProfile()).isActive(true)
                .userName(registerPatientRequest.getUserName()).doctorId(doctorId).build());
    }

    public void inactivePatient(String doctorId, UUID patientId){
        PatientEntity patientEntity = patientEntityRepository.findByDoctorIdAndPatientId(doctorId,patientId);
        patientEntity.setIsActive(false);
        patientEntityRepository.save(patientEntity);
    }

    public PatientEntity getPatientDetails(String doctorId, UUID patientId){
        // get prescriptions + drugs and create new dto and return
        return patientEntityRepository.findByDoctorIdAndPatientId(doctorId, patientId);
    }
}
