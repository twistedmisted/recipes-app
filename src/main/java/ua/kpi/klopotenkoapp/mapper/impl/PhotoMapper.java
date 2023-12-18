package ua.kpi.klopotenkoapp.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.entity.PhotoEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;
import ua.kpi.klopotenkoapp.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class PhotoMapper implements Mapper<PhotoEntity, PhotoDTO> {

    private final RecipeRepository recipeRepository;

    @Override
    public PhotoEntity dtoToEntity(PhotoDTO dto) {
        if (dto == null) {
            return null;
        }
        PhotoEntity entity = new PhotoEntity();
        entity.setId(dto.getId());
        entity.setFilename(dto.getFilename());
        if (!isNull(dto.getRecipeId())){
            entity.setRecipe(recipeRepository.findById(dto.getRecipeId()).get());
        }
        return entity;
    }

    @Override
    public PhotoDTO entityToDto(PhotoEntity entity) {
        if (entity == null) {
            return null;
        }
        PhotoDTO dto = new PhotoDTO();
        dto.setId(entity.getId());
        dto.setFilename(entity.getFilename());
        if (entity.getRecipe() != null) {
            dto.setRecipeId(entity.getRecipe().getId());
        }
        return dto;
    }

    @Override
    public List<PhotoEntity> dtosToEntities(List<PhotoDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<PhotoEntity> entities = new ArrayList<>();
        for (PhotoDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

    @Override
    public List<PhotoDTO> entitiesToDtos(List<PhotoEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<PhotoDTO> dtos = new ArrayList<>();
        for (PhotoEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
