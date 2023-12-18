package ua.kpi.klopotenkoapp.mapper.impl;

import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.StaffDTO;
import ua.kpi.klopotenkoapp.entity.StaffEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;

import java.util.List;

@Component
public class StaffMapper implements Mapper<StaffEntity, StaffDTO> {
    @Override
    public StaffEntity dtoToEntity(StaffDTO dto) {
        if (dto == null) {
            return null;
        }
        StaffEntity entity = new StaffEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setAccessLevel(dto.getAccessLevel());
        return entity;
    }

    @Override
    public StaffDTO entityToDto(StaffEntity entity) {
        if (entity == null) {
            return null;
        }
        return StaffDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .accessLevel(entity.getAccessLevel())
                .build();
    }

    @Override
    public List<StaffEntity> dtosToEntities(List<StaffDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StaffDTO> entitiesToDtos(List<StaffEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
