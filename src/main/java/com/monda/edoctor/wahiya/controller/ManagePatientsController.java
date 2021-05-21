package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.PatientSummary;
import com.monda.edoctor.wahiya.dto.RegisterPatientRequest;
import com.monda.edoctor.wahiya.model.PatientEntity;
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

    @Autowired
    public ManagePatientsService managePatientsService;
// Get patient detail will return only patient basic detail and get prescription by patient id new API move to here?
    // Issue prescription API
    @GetMapping(value = "/doctors/{doctorId}/patients/summary")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientSummary> getPatientsSummaryOfDoctor(@PathVariable("doctorId") String doctorId) {
        return managePatientsService.getPatientsSummaryOfDoctor(doctorId);
    }

    @GetMapping(value = "/doctors/{doctorId}/patients/{patientId}/details")
    @ResponseStatus(code = HttpStatus.OK)
    public PatientEntity getPatientDetails(@PathVariable("doctorId") String doctorId,
                                           @PathVariable("patientId") UUID patientId) {
        return managePatientsService.getPatientDetails(doctorId, patientId);
    }

    @PostMapping(value = "/doctors/{doctorId}/patients/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerPatient(@PathVariable("doctorId") String doctorId,
                                @RequestBody RegisterPatientRequest registerPatientRequest) {
        managePatientsService.registerPatient(registerPatientRequest, doctorId);
    }

    @DeleteMapping(value = "/doctors/{doctorId}/patients/{patientId}/inactive")
    @ResponseStatus(code = HttpStatus.OK)
    public void inactivePatient(@PathVariable("doctorId") String doctorId,
                              @PathVariable("patientId") UUID patientId) {
        managePatientsService.inactivePatient(doctorId, patientId);
    }
}
