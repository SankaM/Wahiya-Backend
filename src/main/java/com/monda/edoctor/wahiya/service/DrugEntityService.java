package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.PaginationRequest;
import com.monda.edoctor.wahiya.dto.RegisterDrugRequest;
import com.monda.edoctor.wahiya.dto.UpdateDrugRequest;
import com.monda.edoctor.wahiya.exception.DrugNotFoundException;
import com.monda.edoctor.wahiya.exception.NoContentException;
import com.monda.edoctor.wahiya.model.DrugEntity;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.repository.DrugEntityRepository;
import com.monda.edoctor.wahiya.repository.specification.DrugSpecification;
import com.monda.edoctor.wahiya.repository.specification.PatientSpecification;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Setter
public class DrugEntityService {

    private static final Logger logger = LoggerFactory.getLogger(DrugEntityService.class);


    @Autowired
    private DrugEntityRepository drugEntityRepository;

    public boolean existsById(UUID id) throws DrugNotFoundException {
        if (!drugEntityRepository.existsById(id)) {
            logger.error("Drug ID not available : {}", id);
            throw new DrugNotFoundException();
        }
        return true;
    }

    public void registerDrug(RegisterDrugRequest registerDrugRequest) {
        DrugEntity drugEntity = drugEntityRepository.save(DrugEntity.builder()
                .name(registerDrugRequest.getName())
                .availableUnits(registerDrugRequest.getAvailableUnits() == null ? 0 : registerDrugRequest.getAvailableUnits())
                .description(registerDrugRequest.getDescription())
                .expiryDate(registerDrugRequest.getExpiryDate())
                .unit(registerDrugRequest.getUnit())
                .unitPrice(registerDrugRequest.getUnitPrice() == null ? 0 : registerDrugRequest.getUnitPrice())
                .imageLink(registerDrugRequest.getImageUrl())
                .isAvailable(true).build());
        logger.debug("Drug added successfully ID: {} Name: {}", drugEntity.getDrugId(), drugEntity.getName());
    }

    public void updateDrugInventory(UpdateDrugRequest updateDrugRequest, UUID drugId) throws DrugNotFoundException {
        if (existsById(drugId)) {
            DrugEntity drugEntity = drugEntityRepository.findById(drugId).get();
            switch (updateDrugRequest.getUpdateType()) {
                case ADD:
                    drugEntity.setAvailableUnits(drugEntity.getAvailableUnits() + updateDrugRequest.getUnit());
                    break;
                case SET:
                    drugEntity.setAvailableUnits(updateDrugRequest.getUnit());
                    break;
                case DEDUCT:
                    Double units = drugEntity.getAvailableUnits() - updateDrugRequest.getUnit();
                    if (units > 0) {
                        drugEntity.setAvailableUnits(units);
                    }
                    break;
                default:
                    break;
            }
            drugEntityRepository.save(drugEntity);
        }
    }


    public List<DrugEntity> searchDrug(String query) {
        List<DrugEntity> drugs = drugEntityRepository.findAll(DrugSpecification.textInAllColumns(query));
        if(drugs.isEmpty()){
            logger.debug("No Drugs available for: {}", query);
            throw new NoContentException("No drugs available for: " + query);
        }
        return drugs;
    }

    public Page<DrugEntity> getAllDrugsWithPagination(PaginationRequest paginationRequest) {
        PageRequest page = PageRequest.of(paginationRequest.getPage(), paginationRequest.getLimit());
        Page<DrugEntity> drugs = drugEntityRepository.findAll(page);
        if(drugs.isEmpty()){
            logger.debug("No Drugs available for");
            throw new NoContentException("No drugs available");
        }
        return drugs;
    }
}
