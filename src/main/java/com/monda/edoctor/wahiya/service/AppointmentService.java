package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.req.UpdateAppointmentStatusReq;
import com.monda.edoctor.wahiya.dto.res.AppointmentRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    public List<AppointmentRes> retrievePastAppointment(UUID doctorId) throws NotFoundException {
        doctorService.existsById(doctorId);

        LocalDateTime now = LocalDate.now().atStartOfDay();

        return appointmentRepository.findBeforeDate(doctorId, now)
                .stream()
                .map(appointment -> AppointmentRes.build(appointment))
                .collect(Collectors.toList());
    }

    public List<AppointmentRes> retrieveFutureAppointment(UUID doctorId) throws NotFoundException {
        doctorService.existsById(doctorId);

        LocalDateTime now = LocalDate.now().atStartOfDay();

        return appointmentRepository.findAfterDate(doctorId, now)
                .stream()
                .map(appointment -> AppointmentRes.build(appointment))
                .collect(Collectors.toList());
    }

    public void updateAppointmentStatus(UUID doctorId, UUID appointmentId, UpdateAppointmentStatusReq req) throws NotFoundException, WrongParameterException {
        doctorService.existsById(doctorId);
        var appointmentOpt = appointmentRepository.findByDoctorIdAndAppointmentId(doctorId, appointmentId);

        if(!appointmentOpt.isPresent()) {
            throw new NotFoundException("No Appointment for specified doctor");
        }

        var appointment = appointmentOpt.get();
        appointment.setStatus(req.getAppointmentStatus());
        appointmentRepository.save(appointment);
    }
}
