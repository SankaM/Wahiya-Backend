package com.monda.edoctor.wahiya.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
    private Integer page = 0;
    private Integer limit = 10;
}
