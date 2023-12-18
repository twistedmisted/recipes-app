package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.repository.RegionRepository;
import ua.kpi.klopotenkoapp.repository.projection.RegionNameOnly;
import ua.kpi.klopotenkoapp.service.RegionService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionsServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<String> getAllRegions() {
        log.debug("Getting all regions");
        List<RegionNameOnly> regions = regionRepository.findProjections();
        if (regions.isEmpty()) {
            log.warn("Cannot find regions");
            return new ArrayList<>();
        }
        log.debug("Regions was successfully found");
        return regions.stream()
                .map(RegionNameOnly::getName)
                .toList();
    }
}
