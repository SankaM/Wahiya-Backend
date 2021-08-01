package com.monda.edoctor.wahiya.dto.req;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    private String userName;
    
    private String password;
}
