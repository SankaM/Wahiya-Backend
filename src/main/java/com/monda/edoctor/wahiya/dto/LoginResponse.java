package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String doctorId;
    private String doctorName;
    private String userName;
    private String location;
    private String imageUrl;
}
