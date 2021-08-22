package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.Config;
import com.monda.edoctor.wahiya.dto.req.NewPrescriptionReq;
import com.monda.edoctor.wahiya.dto.req.UploadFileReq;
import com.monda.edoctor.wahiya.dto.res.DiagnosisRes;
import com.monda.edoctor.wahiya.dto.res.PrescriptionRes;
import com.monda.edoctor.wahiya.dto.res.UploadFileRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.*;
import com.monda.edoctor.wahiya.repository.*;
import lombok.val;
import lombok.var;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PrescriptionService.class);
    @Autowired
    private PatientService patientService;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DosageRepository dosageRepository;

    @Autowired
    private AttachmentService attachmentService;

    public boolean existsById(UUID id) throws NotFoundException {
        if (!prescriptionRepository.existsById(id)) {
            log.error("Prescription ID not available : {}", id);
            throw new NotFoundException("Patient ID not available");
        }

        return true;
    }

    public List<PrescriptionRes> retrieveLastPrescriptions(UUID patientId) throws NotFoundException {
        patientService.existsById(patientId);

        List<PrescriptionEntity> prescriptionEntityList = prescriptionRepository.findByPatientIdOrderByPrescriptionDateAsc(patientId);

        return prescriptionEntityList
                .stream()
                .map(prescription -> PrescriptionRes.buildDetail(prescription))
                .collect(Collectors.toList());
    }

    public List<PrescriptionRes> retrieveCurrentPrescriptions(UUID patientId) throws NotFoundException {
        patientService.existsById(patientId);

        LocalDateTime now = LocalDate.now().atStartOfDay();
        List<PrescriptionEntity> prescriptionEntityList = prescriptionRepository.findNotExpiredPrescription(patientId, now);

        return prescriptionEntityList
                .stream()
                .map(prescription -> PrescriptionRes.buildDetail(prescription))
                .collect(Collectors.toList());
    }

    public PrescriptionRes retrievePrescription(UUID prescriptionId) throws NotFoundException {
        existsById(prescriptionId);

        return PrescriptionRes.buildDetail(prescriptionRepository.getOne(prescriptionId));
    }

    public List<DiagnosisRes> retrieveDiagnosis(String name) {
        List<DiagnosisEntity> diagnosisEntityList = diagnosisRepository.findByLikeName(name);

        return diagnosisEntityList
                .stream()
                .map(diagnosis -> DiagnosisRes.buildDetail(diagnosis))
                .collect(Collectors.toList());
    }

    public DiagnosisRes createOrRetrieve(String name) {
        var diagnosisOpt = diagnosisRepository.findByExactName(name);

        if(diagnosisOpt.isPresent()) {
            return DiagnosisRes.buildDetail(diagnosisOpt.get());
        } else {
            DiagnosisEntity newDiagnosis = DiagnosisEntity.builder().name(StringUtils.capitalize(name)).build();
            newDiagnosis = diagnosisRepository.save(newDiagnosis);

            return DiagnosisRes.buildDetail(newDiagnosis);
        }
    }

    public PrescriptionRes newPrescription(UUID doctorId, UUID patientId, NewPrescriptionReq req, MultipartFile attachmentMultipartFile) throws NotFoundException {
        doctorService.existsById(doctorId);
        patientService.existsById(patientId);

        DoctorEntity doctor = doctorRepository.getOne(doctorId);
        PatientEntity patient = patientRepository.getOne(patientId);
        DiagnosisEntity diagnosis = diagnosisRepository.getOne(req.getDiagnosisId());
        UploadFileRes uploadFileRes = null;
        if(attachmentMultipartFile != null) {
            try {
                UploadFileReq uploadFileReq = new UploadFileReq(attachmentMultipartFile);
                uploadFileRes = attachmentService.uploadFile(uploadFileReq);
            } catch(IOException e) {
                log.error("Error upload attachment", e);
            }
        }

        val prescription = PrescriptionEntity.builder()
                .doctor(doctor)
                .patient(patient)
                .diagnosis(diagnosis)
                .illnessSeverity(PrescriptionEntity.IllnessSeverity.valueOf(req.getIllnessSeverity()))
                .prescriptionDate(LocalDate.now().atStartOfDay())
                .notes(req.getNotes())
                .attachmentId(uploadFileRes != null ? uploadFileRes.getAttachmentDetails().getAttachmentId() : null)
                .doctorCost(doctor.getDoctorCost())
                .build();
        prescriptionRepository.save(prescription);

        AtomicReference<Double> totalDrugCost = new AtomicReference<>(0.0);
        List<DosageEntity> dosageEntityList = req.getTreatmentItemList().stream().map(treatmentItem -> {
            val drugCount = treatmentItem.getTreatmentDays() * treatmentItem.getTimesPerDay() * treatmentItem.getDosageCount();
            val inventory = inventoryRepository.getOne(treatmentItem.getInventoryId());
            inventory.setAvailableUnits(inventory.getAvailableUnits() - drugCount);
            inventoryRepository.save(inventory);

            val drug = inventory.getDrug();
            double drugCost = inventory.getUnitSellPrice() * drugCount;
            totalDrugCost.updateAndGet(v -> (v + drugCost));

            var dosage = DosageEntity.builder()
                    .prescription(prescription)
                    .drug(drug)
                    .treatmentDays(treatmentItem.getTreatmentDays())
                    .timesPerDay(treatmentItem.getTimesPerDay())
                    .dosageCount(treatmentItem.getDosageCount().doubleValue())
                    .dosageRule(DosageEntity.DosageRule.valueOf(treatmentItem.getDosageRule()))
                    .drugCost(drugCost)
                    .build();
            dosageRepository.save(dosage);

            return dosage;
        }).collect(Collectors.toList());

        // calculate last treatment date
        Integer longestDosageTreatmentDays = 0;
        for(DosageEntity dosageEntity: dosageEntityList) {
            if(longestDosageTreatmentDays < dosageEntity.getTreatmentDays()) longestDosageTreatmentDays = dosageEntity.getTreatmentDays();
        }

        prescription.setDrugCost(totalDrugCost.get());
        prescription.setTotalCost(doctor.getDoctorCost() + totalDrugCost.get());
        prescription.setDosageList(dosageEntityList);
        prescription.setLastTreatmentDate(prescription.getPrescriptionDate().plusDays(longestDosageTreatmentDays));
        prescriptionRepository.save(prescription);

        return PrescriptionRes.buildDetail(prescription);
    }

    protected String convertMultipartToFile(MultipartFile multipartFile, String fileName) {
        if(multipartFile.getOriginalFilename().contains(".")) {
            int lastIndexOfDot = multipartFile.getOriginalFilename().lastIndexOf(".");
            String extension = multipartFile.getOriginalFilename().substring(lastIndexOfDot + 1);
            fileName = fileName + "." + extension;
        }

        File file = new File(Config.STORAGE_PATH + Config.PATH_SEPARATOR + fileName);
        try {
            multipartFile.transferTo(file);
        } catch(IOException e) {
            log.error(e.getMessage());
        }

        return fileName;
    }

    public List<PrescriptionRes> getPrescriptionListOfDoctor(UUID doctorId, String patientName, int pageNumber, int itemPerPage) throws NotFoundException {
        doctorService.existsById(doctorId);

        List<PrescriptionEntity> result = new ArrayList<>();

        if(patientName == null || patientName.isEmpty()) {
            result = prescriptionRepository.findByDoctorId(doctorId, PageRequest.of(pageNumber, itemPerPage));
        } else {
            result = prescriptionRepository.findByDoctorIdAndPatientName(doctorId, patientName, PageRequest.of(pageNumber, itemPerPage));
        }

        return result.stream().map(prescriptionEntity -> PrescriptionRes.buildSimple(prescriptionEntity)).collect(Collectors.toList());
    }
}
