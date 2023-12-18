package ua.kpi.klopotenkoapp.mapper.impl;

import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.CategoryDTO;
import ua.kpi.klopotenkoapp.entity.CategoryEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;

import java.util.List;

@Component
public class CategoryMapper implements Mapper<CategoryEntity, CategoryDTO> {

    @Override
    public CategoryEntity dtoToEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public CategoryDTO entityToDto(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public List<CategoryEntity> dtosToEntities(List<CategoryDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CategoryDTO> entitiesToDtos(List<CategoryEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
