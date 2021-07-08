package com.monda.edoctor.wahiya.dto.res;

import lombok.*;

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
