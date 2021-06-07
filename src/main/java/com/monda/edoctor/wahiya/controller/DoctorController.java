package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.service.DoctorEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class DoctorController {

    @Autowired
    private DoctorEntityService doctorEntityService;

    @PostMapping(value = "/doctor")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void  addDoctor(@RequestBody DoctorEntity doctorEntity) {
        doctorEntityService.addDoctor(doctorEntity);
    }
}
