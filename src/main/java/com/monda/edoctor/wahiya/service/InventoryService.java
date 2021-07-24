package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.res.InventoryRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.InventoryEntity;
import com.monda.edoctor.wahiya.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DoctorService doctorService;

    public List<InventoryRes> getInventoryListOfDoctor(UUID doctorId, String query, int pageNumber, int itemPerPage) throws NotFoundException {
        doctorService.existsById(doctorId);

        List<InventoryEntity> inventoryEntityList;

        if(query == null || query.isEmpty()) {
            inventoryEntityList = inventoryRepository.find(doctorId, PageRequest.of(pageNumber, itemPerPage));
        } else {
            inventoryEntityList = inventoryRepository.findByDrugName(doctorId, query, PageRequest.of(pageNumber, itemPerPage));
        }

        return inventoryEntityList.stream().map(inventoryEntity -> InventoryRes.buildSimple(inventoryEntity)).collect(Collectors.toList());
    }
}
