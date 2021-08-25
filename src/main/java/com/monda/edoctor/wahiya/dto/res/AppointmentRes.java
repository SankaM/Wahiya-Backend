package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.AppointmentEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentRes {
    private UUID id;

    private PatientRes patient;

    private String appointmentDate;

    private AppointmentEntity.AppointmentStatus status;

    private UUID prescriptionId;

    public static AppointmentRes build(AppointmentEntity appointment) {
        AppointmentRes res = null;

        if(appointment != null) {
            res = new AppointmentRes();
            res.id = appointment.getId();
            res.patient = PatientRes.buildSimple(appointment.getPatient(), null);
            res.appointmentDate = appointment.getAppointmentDate() != null ? appointment.getAppointmentDate().toString() : null;
            res.status = appointment.getStatus();
            res.prescriptionId = appointment.getPrescription() != null ? appointment.getPrescription().getId() : null;
        }

        return res;
    }
}
