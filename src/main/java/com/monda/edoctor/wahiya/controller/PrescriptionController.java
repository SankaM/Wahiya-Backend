package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.req.NewPrescriptionReq;
import com.monda.edoctor.wahiya.dto.res.DiagnosisRes;
import com.monda.edoctor.wahiya.dto.res.PrescriptionRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.service.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping(value = "/patients/{patientId}/last-prescription")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<PrescriptionRes>> getLastPrescription(@PathVariable("patientId") UUID patientId) throws NotFoundException {
        return new ResponseWrapper<>(true, null, prescriptionService.retrieveLastPrescriptions(patientId));
    }

    @GetMapping(value = "/patients/{patientId}/current-prescription")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<PrescriptionRes>> getCurrentPrescription(@PathVariable("patientId") UUID patientId) throws NotFoundException {
        return new ResponseWrapper<>(true, null, prescriptionService.retrieveCurrentPrescriptions(patientId));
    }

    @GetMapping(value ="/diagnosis")
    public ResponseWrapper<List<DiagnosisRes>> getDiagnosis() {
        return new ResponseWrapper<>(true, null, prescriptionService.retrieveDiagnosis());
    }

    @PostMapping(value = "/doctors/{doctorId}/patients/{patientId}/prescription",
                 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseWrapper<PrescriptionRes> newPrescription(@PathVariable("doctorId") UUID doctorId,
                                @PathVariable("patientId") UUID patientId,
                                @RequestPart("data") NewPrescriptionReq req,
                                @RequestPart(value = "fileAttachment", required = false) MultipartFile multipartFile) throws NotFoundException{

        return new ResponseWrapper<>(true, null, prescriptionService.newPrescription(doctorId, patientId, req, multipartFile));
    }

    @GetMapping(value = "/prescription/{prescriptionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<PrescriptionRes> getPrescription(@PathVariable UUID prescriptionId) throws NotFoundException {
        return new ResponseWrapper<>(true, null, prescriptionService.retrievePrescription(prescriptionId));
    }

    @GetMapping(value = "/doctors/{doctorId}/prescription")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<PrescriptionRes>> getInventoryListOfDoctor(@PathVariable("doctorId") UUID doctorId,
                                                                        @RequestParam(value = "query", required = false) String query,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("itemPerPage") int itemPerPage) throws NotFoundException {

        val data = prescriptionService.getPrescriptionListOfDoctor(doctorId, query, page, itemPerPage);
        return new ResponseWrapper<List<PrescriptionRes>>(true, null, data);
    }
}
