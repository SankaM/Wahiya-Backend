package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "profile", length = 500)
    private String profile;

    @Column(name = "general_work_hour")
    private String generalWorkHour;

    @Column(name = "mobile_phone" , unique = true)
    private String mobilePhone;

    @Column(name = "email" , unique = true)
    private String email;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "doctor_cost")
    private Double doctorCost;

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private List<WorkHourEntity> workHourList;
}
