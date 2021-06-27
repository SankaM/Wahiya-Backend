package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.InvoiceResponse;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.service.PrescriptionEntityService;
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
    public PrescriptionEntityService prescriptionEntityService;

    @GetMapping(value = "/prescription/{prescriptionId}/invoice")
    @ResponseStatus(code = HttpStatus.OK)
    public InvoiceResponse addPrescription(@PathVariable("prescriptionId") UUID prescriptionId) throws NotFoundException {
        return prescriptionEntityService.getInvoice(prescriptionId);
    }


}
