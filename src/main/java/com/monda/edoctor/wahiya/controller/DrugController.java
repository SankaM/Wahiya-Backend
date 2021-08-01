package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.res.DrugRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class DrugController {
    @Autowired
    private DrugService drugService;

    @GetMapping(value = "/drug")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<DrugRes>> searchDrug(@RequestParam(value = "name", required = false) String name) {
        val data = drugService.findDrugByName(name);
        return new ResponseWrapper<List<DrugRes>>(true, null, data);
    }
}
