package com.monda.edoctor.wahiya.dto.res;

import com.monda.edoctor.wahiya.model.DoctorEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRes {
    private UUID id;

    private String name;

    private String profile;

    private String imageURL;

    public static DoctorRes buildSummary(DoctorEntity d) {
        DoctorRes res = null;

        if(d != null) {
            res = new DoctorRes();
            res.id = d.getId();
            res.name = d.getName();
            res.profile = d.getProfile();
            res.imageURL = d.getImageUrl();
        }

        return res;
    }
}
