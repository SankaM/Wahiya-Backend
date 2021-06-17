package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.InvoiceResponse;
import com.monda.edoctor.wahiya.dto.PrescriptionRequest;
import com.monda.edoctor.wahiya.exception.DoctorNotFoundException;
import com.monda.edoctor.wahiya.exception.DrugNotFoundException;
import com.monda.edoctor.wahiya.exception.PatientNotFoundException;
import com.monda.edoctor.wahiya.service.ManagePatientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class InvoiceController {

    @Autowired
    public ManagePatientsService managePatientsService;


    @GetMapping(value = "/prescription/{prescriptionId}/invoice")
    @ResponseStatus(code = HttpStatus.OK)
    public InvoiceResponse addPrescription(@PathVariable("prescriptionId") UUID prescriptionId) throws DoctorNotFoundException, PatientNotFoundException, DrugNotFoundException {
        return managePatientsService.getInvoice(prescriptionId);
    }


}
