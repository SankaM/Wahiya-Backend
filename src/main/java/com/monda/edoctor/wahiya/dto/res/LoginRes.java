package com.monda.edoctor.wahiya.dto.res;

import com.monda.edoctor.wahiya.model.DoctorEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {
    private UUID doctorId;

    private String doctorName;

    private String userName;

    private String location;

    private String imageUrl;

    public static LoginRes build(DoctorEntity d) {
        LoginRes res = null;

        if (d != null) {
            res = new LoginRes();
            res.doctorId = d.getId();
            res.doctorName = d.getName();
            res.location = d.getAddress3();
            res.imageUrl = d.getImageUrl();
        }

        return res;
    }
}
