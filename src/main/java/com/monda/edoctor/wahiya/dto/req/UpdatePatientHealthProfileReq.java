package com.monda.edoctor.wahiya.dto.req;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdatePatientHealthProfileReq {
    private UUID patientId;

    private String healthProfile;
}
