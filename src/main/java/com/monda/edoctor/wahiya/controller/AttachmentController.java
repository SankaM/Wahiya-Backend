package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.req.AttachmentReq;
import com.monda.edoctor.wahiya.dto.req.PatientReq;
import com.monda.edoctor.wahiya.dto.req.UploadFileReq;
import com.monda.edoctor.wahiya.dto.res.AttachmentRes;
import com.monda.edoctor.wahiya.dto.res.PatientAttachmentsRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.dto.res.UploadFileRes;
import com.monda.edoctor.wahiya.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping(value = "/doctors/{doctorId}/patients/{patientId}/upload")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<UploadFileRes> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                              @PathVariable("doctorId") UUID doctorId,
                                              @PathVariable("patientId") UUID patientId) throws IOException {
        UploadFileReq uploadFileReq = UploadFileReq.builder().doctorId(doctorId).patientId(patientId).file(file).build();
        UploadFileRes uploadFileRes = attachmentService.uploadFile(uploadFileReq);
        return new ResponseWrapper<>(true, "Success", uploadFileRes);
    }

    @GetMapping(value = "/doctors/{doctorId}/patients/{patientId}/attachments")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<PatientAttachmentsRes> getPatientAttachments(
            @PathVariable("doctorId") UUID doctorId,
            @PathVariable("patientId") UUID patientId) throws IOException {
        PatientReq patientReq = PatientReq.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .build();
        PatientAttachmentsRes response = attachmentService.getPatientAttachments(patientReq);
        return new ResponseWrapper<>(true, "Success", response);
    }

    @GetMapping(value = "/doctors/{doctorId}/patients/{patientId}/attachments/{attachmentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<byte[]> getPatientAttachment(
            @PathVariable("doctorId") UUID doctorId,
            @PathVariable("patientId") UUID patientId,
            @PathVariable("attachmentId") UUID attachmentId) throws IOException {
        AttachmentReq attachmentReq = AttachmentReq.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .attachmentId(attachmentId)
                .build();
        AttachmentRes attachmentRes = attachmentService.getAttachment(attachmentReq);

        return ResponseEntity.ok()
                .contentType(contentType(attachmentRes.getAttachmentKey()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + attachmentRes.getAttachmentKey() + "\"")
                .body(attachmentRes.getDownloadInputStream().toByteArray());
    }

    private MediaType contentType(String keyName) {
        String[] arr = keyName.split("\\.");
        String type = arr[arr.length-1];
        switch(type) {
            case "txt": return MediaType.TEXT_PLAIN;
            case "png": return MediaType.IMAGE_PNG;
            case "jpg": return MediaType.IMAGE_JPEG;
            default: return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
