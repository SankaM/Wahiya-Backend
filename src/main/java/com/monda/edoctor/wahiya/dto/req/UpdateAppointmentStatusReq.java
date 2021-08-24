package com.monda.edoctor.wahiya.dto.req;

import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.model.AppointmentEntity;
import lombok.Data;

@Data
public class UpdateAppointmentStatusReq {
    private String status;

    public AppointmentEntity.AppointmentStatus getAppointmentStatus() throws WrongParameterException {
        if(status != null) {
            try {
                return AppointmentEntity.AppointmentStatus.valueOf(status);
            } catch (Exception e) {
                throw new WrongParameterException("Wrong field value");
            }
        }

        return null;
    }
}
