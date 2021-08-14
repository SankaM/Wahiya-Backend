package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "attachments", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Column(name = "attachment_key")
    private String attachmentKey;
}
