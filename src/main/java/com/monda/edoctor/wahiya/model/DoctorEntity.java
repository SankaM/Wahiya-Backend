package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "doctor", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity {

    @Id
    @Column(name="doctor_id")
    private UUID doctorId;

    @Column(name="name")
    private String name;

    @Column(name="user_name")
    private String userName;

    @Column(name="mobile_number")
    private Integer mobile;

    @Column(name="email")
    private String email;

    @Column(name="address_1")
    private String address1;

    @Column(name="address_2")
    private String address2;

    @Column(name="address_3")
    private String address3;

    @Column(name="zip_code")
    private String zip_code;

    @Column(name="image_link")
    private String imageLink;

    @Column(name="is_active")
    private Boolean isActive;

    @Column(name="password")
    private String password;

}
