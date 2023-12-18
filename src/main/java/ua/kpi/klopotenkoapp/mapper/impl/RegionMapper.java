package ua.kpi.klopotenkoapp.mapper.impl;

import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.RegionDTO;
import ua.kpi.klopotenkoapp.entity.RegionEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;

import java.util.List;

@Component
public class RegionMapper implements Mapper<RegionEntity, RegionDTO> {

    @Override
    public RegionEntity dtoToEntity(RegionDTO dto) {
        if (dto == null) {
            return null;
        }
        RegionEntity entity = new RegionEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public RegionDTO entityToDto(RegionEntity entity) {
        if (entity == null) {
            return null;
        }
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public List<RegionEntity> dtosToEntities(List<RegionDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RegionDTO> entitiesToDtos(List<RegionEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
