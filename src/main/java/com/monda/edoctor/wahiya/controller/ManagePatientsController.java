package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.PatientSummary;
import com.monda.edoctor.wahiya.dto.RegisterPatientRequest;
import com.monda.edoctor.wahiya.model.BasicPatientInfoEntity;
import com.monda.edoctor.wahiya.service.ManagePatientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class ManagePatientsController {

    private ManagePatientsService managePatientsService;

    @Autowired
    public ManagePatientsController(ManagePatientsService managePatientsService){
        this.managePatientsService = managePatientsService;
    }

    @GetMapping(value = "/doctor/{doctorId}/patients/summary")
    @ResponseStatus(code = HttpStatus.OK)
    public List<BasicPatientInfoEntity> getPatientsSummaryOfDoctor(@PathVariable("doctorId") String doctorId) {
        return managePatientsService.getPatientsSummaryOfDoctor(doctorId);
    }

    @PostMapping(value = "/doctor/{doctorId}/patient/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerPatient(@PathVariable("doctorId") String doctorId,
                                @RequestBody RegisterPatientRequest registerPatientRequest) {
        managePatientsService.registerPatient(BasicPatientInfoEntity.builder().name(registerPatientRequest.getName())
                .doctorId(doctorId).age(registerPatientRequest.getAge()).build());
    }

    @DeleteMapping(value = "/doctor/{doctorId}/patient/{patientId}/remove")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePatient(@PathVariable("doctorId") String doctorId,
                              @PathVariable("patientId") UUID patientId) {
        managePatientsService.deletePatient(patientId);
    }
}
