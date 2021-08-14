package com.monda.edoctor.wahiya.dto.req;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileReq {
    private UUID doctorId;
    private UUID patientId;
    private MultipartFile file;
}
