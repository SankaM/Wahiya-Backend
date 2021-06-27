package com.monda.edoctor.wahiya.dto;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private String id;
    private String name;
    private String imageURL;
}
