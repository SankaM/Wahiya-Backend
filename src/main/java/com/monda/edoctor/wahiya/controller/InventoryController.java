package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.req.NewBatchInventoryReq;
import com.monda.edoctor.wahiya.dto.res.DrugRes;
import com.monda.edoctor.wahiya.dto.res.InventoryRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.service.DrugService;
import com.monda.edoctor.wahiya.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
    private InventoryService inventoryService;

    @GetMapping(value = "/doctors/{doctorId}/inventory")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<InventoryRes>> getInventoryListOfDoctor(@PathVariable("doctorId") UUID doctorId,
                                                                        @RequestParam(value = "query", required = false) String query,
                                                                        @RequestParam(value = "field", required = false) String fieldAsString,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("itemPerPage") int itemPerPage) throws NotFoundException, WrongParameterException {

        InventoryService.SearchDrugField field = null;
        if(fieldAsString != null) {
            try {
                field = InventoryService.SearchDrugField.valueOf(fieldAsString);
            } catch (Exception e) {
                throw new WrongParameterException("Wrong field value");
            }
        }
        val data = inventoryService.getInventoryListOfDoctor(doctorId, query, field, page, itemPerPage);
        return new ResponseWrapper<List<InventoryRes>>(true, null, data);
    }

    @PostMapping(value = "/doctors/{doctorId}/inventory/batch")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseWrapper newBatchInventory(@PathVariable("doctorId") UUID doctorId,
                                             @RequestBody NewBatchInventoryReq req) throws NotFoundException {

        inventoryService.newBatchInventory(doctorId, req);
        return new ResponseWrapper(true, null, null);
    }

    @GetMapping(value = "/doctors/{doctorId}/inventory/{inventoryId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<InventoryRes> getInventory(@PathVariable("doctorId") UUID doctorId,
                                                      @PathVariable("inventoryId") UUID inventoryId) throws NotFoundException {

        val data = inventoryService.getInventory(doctorId, inventoryId);
        return new ResponseWrapper<>(true, null, data);
    }
}
