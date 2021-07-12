package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.res.PrescriptionRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.service.PrescriptionService;
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
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping(value = "/patients/{patientId}/history")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PrescriptionRes> getPatientMedicalHistory(@PathVariable("patientId") UUID patientId) throws NotFoundException {
        return prescriptionService.retrievePrescriptions(patientId);
    }
}
