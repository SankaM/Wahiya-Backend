package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.res.PatientRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.service.PatientService;
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
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping(value = "/doctors/{doctorId}/patients/summary")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientRes> getPatientsSummaryOfDoctor(@PathVariable("doctorId") UUID doctorId) throws NotFoundException {
        return patientService.getPatientsOfDoctor(doctorId);
    }

    @GetMapping(value = "/patients/search")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientRes> searchPatient(@RequestParam("query") String query, @RequestParam("field") String fieldAsString) throws WrongParameterException {
        PatientService.SearchPatientField field;
        try {
            field = PatientService.SearchPatientField.valueOf(fieldAsString);
        } catch (Exception e) {
            throw new WrongParameterException("Wrong field value");
        }

        return patientService.searchPatient(query, field);
    }
}
