package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.PaginationRequest;
import com.monda.edoctor.wahiya.dto.RegisterDrugRequest;
import com.monda.edoctor.wahiya.dto.UpdateDrugRequest;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.DrugEntity;
import com.monda.edoctor.wahiya.service.DrugEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public DrugEntityService drugEntityService;

    @PostMapping(value = "/inventory/drug/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerDrug(@RequestBody RegisterDrugRequest registerDrugRequest) {
        drugEntityService.registerDrug(registerDrugRequest);
    }


    @PutMapping(value = "/inventory/drug/{drugId}/update")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateDrug(@PathVariable("drugId") UUID drugId,
                           @RequestBody UpdateDrugRequest updateDrugRequest) throws NotFoundException {
        drugEntityService.updateDrugInventory(updateDrugRequest, drugId);
    }

    @GetMapping(value = "/inventory/search")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DrugEntity> search(@RequestParam("query") String query) {
        return drugEntityService.searchDrug(query);
    }

    @GetMapping(value = "/inventory/drugs")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<DrugEntity> getDrugsWithPagination(PaginationRequest paginationRequest) {
        return drugEntityService.getAllDrugsWithPagination(paginationRequest);
    }
}
