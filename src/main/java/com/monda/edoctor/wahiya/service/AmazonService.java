package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.client.AmazonClient;
import com.monda.edoctor.wahiya.dto.req.LoginReq;
import com.monda.edoctor.wahiya.dto.req.UpdatePasswordReq;
import com.monda.edoctor.wahiya.dto.req.UploadFileReq;
import com.monda.edoctor.wahiya.dto.res.UploadFileRes;
import com.monda.edoctor.wahiya.exception.LoginException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class AmazonService {
    @Autowired
    private AmazonClient amazonClient;

    public UploadFileRes uploadFile(UploadFileReq uploadFileReq) throws IOException {
        String fileUrl = amazonClient.uploadFile(uploadFileReq);
       return UploadFileRes.builder().url(fileUrl).build();
    }
}
