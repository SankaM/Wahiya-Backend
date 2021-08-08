package com.monda.edoctor.wahiya.dto.req;

import lombok.Data;

@Data
public class UpdatePasswordReq {
    private String oldPassword;

    private String newPassword;
}
