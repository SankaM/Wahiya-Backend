package com.monda.edoctor.wahiya.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
    int page = 0;
    int limit = 10;
}
