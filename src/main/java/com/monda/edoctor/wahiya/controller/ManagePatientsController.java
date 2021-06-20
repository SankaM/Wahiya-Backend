package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.MedicalHistoryResponse;
import com.monda.edoctor.wahiya.dto.PatientSummary;
import com.monda.edoctor.wahiya.dto.PrescriptionRequest;
import com.monda.edoctor.wahiya.dto.RegisterPatientRequest;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.service.ManagePatientsService;
import com.monda.edoctor.wahiya.service.PrescriptionEntityService;
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

    @Autowired
    public PrescriptionEntityService prescriptionEntityService;


    @PostMapping(value = "/doctors/{doctorId}/patients/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerPatient(@PathVariable("doctorId") UUID doctorId,
                                @RequestBody RegisterPatientRequest registerPatientRequest) {
        managePatientsService.registerPatient(registerPatientRequest, doctorId);
    }

    // Get patient detail will return only patient basic detail and get prescription by patient id new API move to here?
    // Issue prescription API
    @GetMapping(value = "/doctors/{doctorId}/patients/summary")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientSummary> getPatientsSummaryOfDoctor(@PathVariable("doctorId") UUID doctorId) throws NotFoundException {
        return managePatientsService.getPatientsSummaryOfDoctor(doctorId);
    }

    @GetMapping(value = "/doctors/{doctorId}/patients/{patientId}/details")
    @ResponseStatus(code = HttpStatus.OK)
    public PatientEntity getPatientDetails(@PathVariable("doctorId") UUID doctorId,
                                           @PathVariable("patientId") UUID patientId) throws NotFoundException {
        return managePatientsService.getPatientDetails(doctorId, patientId);
    }

    @DeleteMapping(value = "/doctors/{doctorId}/patients/{patientId}/inactive")
    @ResponseStatus(code = HttpStatus.OK)
    public void inactivePatient(@PathVariable("doctorId") UUID doctorId,
                                @PathVariable("patientId") UUID patientId) throws NotFoundException {
        managePatientsService.inactivePatient(doctorId, patientId);
    }

    @GetMapping(value = "/patients/search")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientEntity> searchPatient(@RequestParam("query") String query) {
        return managePatientsService.searchPatient(query);
    }

    @PostMapping(value = "/doctors/{doctorId}/patients/{patientId}/prescription")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addPrescription(@PathVariable("doctorId") UUID doctorId,
                                @PathVariable("patientId") UUID patientId,
                                @RequestBody PrescriptionRequest prescriptionRequest) throws NotFoundException {
        prescriptionEntityService.addPrescription(doctorId, patientId, prescriptionRequest);
    }

    @GetMapping(value = "/patients/{patientId}/history")
    @ResponseStatus(code = HttpStatus.OK)
    public MedicalHistoryResponse getPatientMedicalHistory(@PathVariable("patientId") UUID patientId) throws NotFoundException {
        return managePatientsService.getPatientMedicalHistory(patientId);
    }

}
