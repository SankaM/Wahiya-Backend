package com.monda.edoctor.wahiya.dto.req;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PatientReq {
    private UUID doctorId;
    private UUID patientId;
}
