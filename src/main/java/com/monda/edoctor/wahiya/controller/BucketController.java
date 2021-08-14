package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.client.AmazonClient;
import com.monda.edoctor.wahiya.dto.req.UploadFileReq;
import com.monda.edoctor.wahiya.dto.res.DrugRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.dto.res.UploadFileRes;
import com.monda.edoctor.wahiya.service.AmazonService;
import com.monda.edoctor.wahiya.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class BucketController {
    @Autowired
    private AmazonService amazonService;

    @PostMapping(value = "/doctor/{doctorId}/patient/{patientId}/upload")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<UploadFileRes> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                              @PathVariable("doctorId") UUID doctorId,
                                              @PathVariable("patientId") UUID patientId) throws IOException {
        UploadFileReq uploadFileReq = UploadFileReq.builder().doctorId(doctorId).patientId(patientId).file(file).build();
        UploadFileRes uploadFileRes = amazonService.uploadFile(uploadFileReq);
        return new ResponseWrapper<>(true, null, uploadFileRes);
    }
}
