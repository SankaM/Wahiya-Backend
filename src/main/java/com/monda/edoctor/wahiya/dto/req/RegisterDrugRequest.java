package com.monda.edoctor.wahiya.dto.req;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDrugRequest {
    private String name;
    private String description;
    private LocalDate expiryDate;
    private Double availableUnits;
    private String unit;
    private Double unitPrice;
    private String imageUrl;
}
