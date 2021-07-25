package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.res.DrugRes;
import com.monda.edoctor.wahiya.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DrugService {
    @Autowired
    private DrugRepository drugRepository;

    public List<DrugRes> findDrugByName(String name) {
        if(name != null && name.length() >= 3) {
            return drugRepository.findDrugByName(name).stream().map(d -> DrugRes.buildDetail(d)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
