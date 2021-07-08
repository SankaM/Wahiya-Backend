package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.res.DrugResponse;
import com.monda.edoctor.wahiya.dto.req.PaginationRequest;
import com.monda.edoctor.wahiya.dto.req.RegisterDrugRequest;
import com.monda.edoctor.wahiya.dto.req.UpdateDrugRequest;
import com.monda.edoctor.wahiya.exception.DuplicateContentException;
import com.monda.edoctor.wahiya.exception.NoContentException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.DrugEntity;
import com.monda.edoctor.wahiya.repository.DrugEntityRepository;
import com.monda.edoctor.wahiya.repository.specification.DrugSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DrugEntityService {

    @Autowired
    private DrugEntityRepository drugEntityRepository;

    // ============================================================================================================== OK

    // ======================================================================================================== PROGRESS

    // ========================================================================================================= NOT YET
    public boolean existsById(UUID id) throws NotFoundException {
        if (!drugEntityRepository.existsById(id)) {
            log.error("Drug ID not available : {}", id);
            throw new NotFoundException("Requested drug ID not available");
        }
        return true;
    }

    public DrugEntity save(DrugEntity drugEntity) throws DuplicateContentException {
        try {
            return drugEntityRepository.saveAndFlush(drugEntity);
        } catch (DataIntegrityViolationException e) {
            log.error("Duplicate Record : {}", e.getMessage());
            throw new DuplicateContentException("Drug already available :" + drugEntity.getName());
        }
    }

    public DrugResponse getDrugResponse(UUID id) {
        DrugEntity drugEntity = drugEntityRepository.findById(id).get();

        return DrugResponse.builder()
                .id(drugEntity.getId())
                .description(drugEntity.getDescription())
//                .expiryDate(drugEntity.getExpiryDate())
//                .imageUrl(drugEntity.getImageLink())
                .name(drugEntity.getName())
//                .unit(drugEntity.getUnit())
//                .unitPrice(drugEntity.getUnitPrice())
                .build();
    }

    public void registerDrug(RegisterDrugRequest registerDrugRequest) {
//        DrugEntity drugEntity = save(DrugEntity.builder()
//                .name(registerDrugRequest.getName())
//                .availableUnits(registerDrugRequest.getAvailableUnits() == null ? 0 : registerDrugRequest.getAvailableUnits())
//                .description(registerDrugRequest.getDescription())
//                .expiryDate(registerDrugRequest.getExpiryDate())
//                .unit(registerDrugRequest.getUnit())
//                .unitPrice(registerDrugRequest.getUnitPrice() == null ? 0 : registerDrugRequest.getUnitPrice())
//                .imageLink(registerDrugRequest.getImageUrl())
//                .isAvailable(true).build());
//        log.debug("Drug added successfully ID: {} Name: {}", drugEntity.getId(), drugEntity.getName());
    }

    public void updateDrugInventory(UpdateDrugRequest updateDrugRequest, UUID drugId) throws NotFoundException {
//        if (existsById(drugId)) {
//            DrugEntity drugEntity = drugEntityRepository.findById(drugId).get();
//            switch (updateDrugRequest.getUpdateType()) {
//                case ADD:
//                    drugEntity.setAvailableUnits(drugEntity.getAvailableUnits() + updateDrugRequest.getUnit());
//                    break;
//                case SET:
//                    drugEntity.setAvailableUnits(updateDrugRequest.getUnit());
//                    break;
//                case DEDUCT:
//                    Double units = drugEntity.getAvailableUnits() - updateDrugRequest.getUnit();
//                    if (units > 0) {
//                        drugEntity.setAvailableUnits(units);
//                    }
//                    break;
//                default:
//                    break;
//            }
//            drugEntityRepository.save(drugEntity);
//        }
    }


    public List<DrugEntity> searchDrug(String query) {
        List<DrugEntity> drugs = drugEntityRepository.findAll(DrugSpecification.textInAllColumns(query));
        if (drugs.isEmpty()) {
            log.debug("No Drugs available for: {}", query);
            throw new NoContentException("No drugs available for: " + query);
        }
        return drugs;
    }

    public Page<DrugEntity> getAllDrugsWithPagination(PaginationRequest paginationRequest) {
        PageRequest page = PageRequest.of(paginationRequest.getPage(), paginationRequest.getLimit());
        Page<DrugEntity> drugs = drugEntityRepository.findAll(page);
        if (drugs.isEmpty()) {
            log.debug("No Drugs available for");
            throw new NoContentException("No drugs available");
        }
        return drugs;
    }
}
