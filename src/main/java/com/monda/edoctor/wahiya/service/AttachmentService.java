package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.client.AmazonClient;
import com.monda.edoctor.wahiya.dto.req.*;
import com.monda.edoctor.wahiya.dto.res.AttachmentRes;
import com.monda.edoctor.wahiya.dto.res.PatientAttachmentsRes;
import com.monda.edoctor.wahiya.dto.res.UploadFileRes;
import com.monda.edoctor.wahiya.model.AttachmentEntity;
import com.monda.edoctor.wahiya.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttachmentService {
    @Autowired
    private AmazonClient amazonClient;
    @Autowired
    private AttachmentRepository attachmentRepository;

    public UploadFileRes uploadFile(UploadFileReq uploadFileReq) throws IOException {
        AttachmentDetails attachmentDetails = amazonClient.uploadFile(uploadFileReq);
        AttachmentEntity attachmentEntity = AttachmentEntity.builder()
                .attachmentKey(attachmentDetails.getAttachmentKey())
                .attachmentName(attachmentDetails.getAttachmentName())
                .patientId(uploadFileReq.getPatientId())
                .doctorId(uploadFileReq.getDoctorId()).build();
        AttachmentEntity savedEntity = attachmentRepository.save(attachmentEntity);
        attachmentDetails.setAttachmentId(savedEntity.getId());
        return UploadFileRes.builder().attachmentDetails(attachmentDetails).build();
    }

    public PatientAttachmentsRes getPatientAttachments(PatientReq patientReq){
        List<AttachmentEntity> attachmentEntities = attachmentRepository.findByDoctorIdAndPatientId(patientReq.getDoctorId(), patientReq.getPatientId());
        return  PatientAttachmentsRes.builder()
                .attachments(
                        attachmentEntities.stream()
                                .map(attachmentEntity -> AttachmentDetails.builder()
                                        .attachmentId(attachmentEntity.getId())
                                        .attachmentName(attachmentEntity.getAttachmentName())
                                        .build())
                                .collect(Collectors.toList())).build();
    }

    public AttachmentRes getAttachment(AttachmentReq attachmentReq){
        Optional<AttachmentEntity> attachmentEntity = attachmentRepository
                .findById(attachmentReq.getAttachmentId());
        return AttachmentRes.builder()
                .attachmentKey(attachmentEntity.get().getAttachmentKey())
                .downloadInputStream(amazonClient.fetchFile(attachmentEntity.get()))
                .build();
    }
}
