package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.req.NewBatchInventoryReq;
import com.monda.edoctor.wahiya.dto.res.InventoryRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.InventoryBatchEntity;
import com.monda.edoctor.wahiya.model.InventoryEntity;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import com.monda.edoctor.wahiya.repository.DrugRepository;
import com.monda.edoctor.wahiya.repository.InventoryBatchRepository;
import com.monda.edoctor.wahiya.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryBatchRepository inventoryBatchRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DrugRepository drugRepository;

    public static enum SearchDrugField {
        NAME, TYPE, MEASUREMENT
    }

    public boolean existsById(UUID id) throws NotFoundException {
        if (!inventoryRepository.existsById(id)) {
            log.error("Inventory ID not available : {}", id);
            throw new NotFoundException("Inventory ID not available");
        }

        return true;
    }

    public List<InventoryRes> getInventoryListOfDoctor(UUID doctorId, String query, SearchDrugField field, int pageNumber, int itemPerPage) throws NotFoundException {
        doctorService.existsById(doctorId);

        List<InventoryEntity> inventoryEntityList = new ArrayList<>();

        if(query == null || query.isEmpty()) {
            inventoryEntityList = inventoryRepository.find(doctorId, PageRequest.of(pageNumber, itemPerPage));
        } else if(field == SearchDrugField.NAME) {
            inventoryEntityList = inventoryRepository.findByDrugName(doctorId, query, PageRequest.of(pageNumber, itemPerPage));
        } else if(field == SearchDrugField.TYPE) {
            inventoryEntityList = inventoryRepository.findByDrugType(doctorId, query, PageRequest.of(pageNumber, itemPerPage));
        } else if(field == SearchDrugField.MEASUREMENT) {
            inventoryEntityList = inventoryRepository.findByDrugMeasurement(doctorId, Double.parseDouble(query), PageRequest.of(pageNumber, itemPerPage));
        }

        return inventoryEntityList.stream().map(inventoryEntity -> InventoryRes.buildSimple(inventoryEntity)).collect(Collectors.toList());
    }

    public void newBatchInventory(UUID doctorId, NewBatchInventoryReq req) throws NotFoundException {
        doctorService.existsById(doctorId);

        if(!drugRepository.existsById(UUID.fromString(req.getDrugId()))) {
            log.error("Drug ID not available : {}", req.getDrugId());
            throw new NotFoundException("Drug ID not available");
        }

        // Inventory entity
        val inventoryOpt = inventoryRepository.findByDoctorIdAndDrugId(doctorId, UUID.fromString(req.getDrugId()));
        InventoryEntity inventory;

        if(inventoryOpt.isPresent()) {
            inventory = inventoryOpt.get();
        } else {
            inventory = InventoryEntity.builder()
                    .doctor(doctorRepository.getOne(doctorId))
                    .drug(drugRepository.getOne(UUID.fromString(req.getDrugId())))
                    .availableUnits(0.0)
                    .isAvailable(true)
                    .build();
            inventory = inventoryRepository.save(inventory);
        }

        inventory.setLastUpdated(LocalDateTime.now());
        inventory.setUnitSellPrice(req.getUnitSellPrice());
        inventory.setUnitPriceCurrency(req.getUnitSellCurrency());

        // InventoryBatch entity
        InventoryBatchEntity inventoryBatch = InventoryBatchEntity.builder()
                .unitCounts(req.getUnitCount())
                .batchDate(LocalDate.parse(req.getBatchDate()))
                .expiryDate(LocalDate.parse(req.getExpiryDate()))
                .unitBuyPrice(req.getUnitBuyPrice())
                .unitPriceCurrency(req.getUnitBuyCurrency())
                .inventory(inventory)
                .build();

        inventoryBatch = inventoryBatchRepository.save(inventoryBatch);

        // Update available unit of Inventory
        inventory.setAvailableUnits(inventory.getAvailableUnits() + inventoryBatch.getUnitCounts());
        inventoryRepository.save(inventory);
    }

    public InventoryRes getInventory(@PathVariable("doctorId") UUID doctorId, @PathVariable("inventoryId") UUID inventoryId) throws NotFoundException {
        doctorService.existsById(doctorId);
        existsById(inventoryId);

        return InventoryRes.buildDetail(inventoryRepository.findById(inventoryId).get());
    }
}
