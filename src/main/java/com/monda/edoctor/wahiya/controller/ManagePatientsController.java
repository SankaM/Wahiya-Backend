package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.res.MedicalHistoryResponse;
import com.monda.edoctor.wahiya.dto.res.PatientResponse;
import com.monda.edoctor.wahiya.dto.req.PrescriptionRequest;
import com.monda.edoctor.wahiya.dto.req.RegisterPatientRequest;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
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

    // ============================================================================================================== OK
    @GetMapping(value = "/doctors/{doctorId}/patients/summary")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientResponse> getPatientsSummaryOfDoctor(@PathVariable("doctorId") UUID doctorId) throws NotFoundException {

        return managePatientsService.getPatientsOfDoctor(doctorId);
    }

    @GetMapping(value = "/patients/search")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientResponse> searchPatient(@RequestParam("query") String query,
                                             @RequestParam("field") String fieldAsString) throws WrongParameterException {

        ManagePatientsService.SearchPatientField field;
        try {
            field = ManagePatientsService.SearchPatientField.valueOf(fieldAsString);
        } catch (Exception e) {
            throw new WrongParameterException("Wrong field value");
        }

        return managePatientsService.searchPatient(query, field);
    }

    @GetMapping(value = "/doctors/{doctorId}/patients/{patientId}/details")
    @ResponseStatus(code = HttpStatus.OK)
    public PatientResponse getPatientDetails(@PathVariable("doctorId") UUID doctorId,
                                           @PathVariable("patientId") UUID patientId) throws NotFoundException {

        return managePatientsService.getPatientDetails(doctorId, patientId);
    }

    // ======================================================================================================== PROGRESS
    @GetMapping(value = "/patients/{patientId}/history")
    @ResponseStatus(code = HttpStatus.OK)
    public MedicalHistoryResponse getPatientMedicalHistory(@PathVariable("patientId") UUID patientId) throws NotFoundException {
        return managePatientsService.getPatientMedicalHistory(patientId);
    }

    // ========================================================================================================= NOT YET
    @PostMapping(value = "/doctors/{doctorId}/patients/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerPatient(@PathVariable("doctorId") UUID doctorId, @RequestBody RegisterPatientRequest registerPatientRequest) {
        managePatientsService.registerPatient(registerPatientRequest, doctorId);
    }

    @DeleteMapping(value = "/doctors/{doctorId}/patients/{patientId}/inactive")
    @ResponseStatus(code = HttpStatus.OK)
    public void inactivePatient(@PathVariable("doctorId") UUID doctorId, @PathVariable("patientId") UUID patientId) throws NotFoundException {
        managePatientsService.inactivePatient(doctorId, patientId);
    }

    @PostMapping(value = "/doctors/{doctorId}/patients/{patientId}/prescription")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addPrescription(@PathVariable("doctorId") UUID doctorId, @PathVariable("patientId") UUID patientId, @RequestBody PrescriptionRequest prescriptionRequest) throws NotFoundException {
        prescriptionEntityService.addPrescription(doctorId, patientId, prescriptionRequest);
    }
}
