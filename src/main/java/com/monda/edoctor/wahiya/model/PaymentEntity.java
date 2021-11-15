package com.monda.edoctor.wahiya.model;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "payment", schema = "kahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private static final String ORDER_PREFIX = "ORD-";

    public enum PaymentProvider {
        PAYHERE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private PrescriptionEntity prescription;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "generated_date")
    private LocalDateTime generatedDate;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "result_date")
    private LocalDateTime resultDate;

    @Column(name = "payment_provider")
    @Enumerated(EnumType.STRING)
    private PaymentEntity.PaymentProvider paymentProvider;

    @Column(name = "payment_result_metadata")
    private String paymentResultMetadata;

    public String generateOrderNumber() {
        return ORDER_PREFIX + StringUtils.leftPad("" + id, 8, "0");
    }
}
