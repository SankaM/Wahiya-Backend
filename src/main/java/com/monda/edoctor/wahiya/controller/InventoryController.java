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
public class InventoryController {

    @Autowired
    public ManagePatientsService managePatientsService;
// Doctor Interface to manage inventory
    // Get inventory summary by pages, expires soon first by default
}
