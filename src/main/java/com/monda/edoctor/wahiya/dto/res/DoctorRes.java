package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.DoctorEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorRes {
    private UUID id;

    private String name;

    private String profile;

    private String imageURL;

    public static DoctorRes buildSimple(DoctorEntity d) {
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
