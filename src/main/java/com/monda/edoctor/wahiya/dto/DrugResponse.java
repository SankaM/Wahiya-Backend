package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugResponse {
    private String name;
    private String description;
    private LocalDate expiryDate;
    private String unit;
    private Double unitPrice;
    private String imageUrl;
}
