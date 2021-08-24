package com.monda.edoctor.wahiya.controller;

import com.monda.edoctor.wahiya.dto.req.UpdateAppointmentStatusReq;
import com.monda.edoctor.wahiya.dto.req.UpdateDoctorReq;
import com.monda.edoctor.wahiya.dto.res.AppointmentRes;
import com.monda.edoctor.wahiya.dto.res.DoctorRes;
import com.monda.edoctor.wahiya.dto.res.ResponseWrapper;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.exception.WrongParameterException;
import com.monda.edoctor.wahiya.service.AppointmentService;
import com.monda.edoctor.wahiya.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(value = "/doctors/{doctorId}/profile")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<DoctorRes> getProfile(@PathVariable("doctorId") UUID doctorId) throws NotFoundException {
        return new ResponseWrapper(true, null, doctorService.getProfile(doctorId));
    }

    @PutMapping(value = "/doctors/{doctorId}/profile")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<DoctorRes> updateProfile(@PathVariable("doctorId") UUID doctorId,
                                                    @RequestBody UpdateDoctorReq req) throws NotFoundException {

        return new ResponseWrapper(true, null, doctorService.updateProfile(doctorId, req));
    }

    @GetMapping(value = "/doctors/{doctorId}/appointment/past")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<AppointmentRes>> getPastAppointment(@PathVariable("doctorId") UUID doctorId) throws NotFoundException {
        return new ResponseWrapper<>(true, null, appointmentService.retrievePastAppointment(doctorId));
    }

    @GetMapping(value = "/doctors/{doctorId}/appointment/upcoming")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper<List<AppointmentRes>> getFutureAppointment(@PathVariable("doctorId") UUID doctorId) throws NotFoundException {
        return new ResponseWrapper<>(true, null, appointmentService.retrieveFutureAppointment(doctorId));
    }

    @PutMapping(value = "/doctors/{doctorId}/appointment/{appointmentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseWrapper updateAppointment(@PathVariable("doctorId") UUID doctorId,
                                             @PathVariable("appointmentId") UUID appointmentId,
                                             @RequestBody UpdateAppointmentStatusReq req) throws NotFoundException, WrongParameterException {
        appointmentService.updateAppointmentStatus(doctorId, appointmentId, req);
        return new ResponseWrapper<>(true, null, null);
    }
}
