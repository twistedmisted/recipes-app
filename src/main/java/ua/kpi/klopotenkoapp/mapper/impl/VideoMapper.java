package ua.kpi.klopotenkoapp.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;
import ua.kpi.klopotenkoapp.entity.VideoEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;
import ua.kpi.klopotenkoapp.repository.RecipeRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class VideoMapper implements Mapper<VideoEntity, VideoDTO> {

    private final RecipeRepository recipeRepository;

    @Override
    public VideoEntity dtoToEntity(VideoDTO dto) {
        if (dto == null) {
            return null;
        }
        VideoEntity entity = new VideoEntity();
        entity.setId(dto.getId());
        entity.setFilename(dto.getFilename());
        if (!isNull(dto.getRecipeId())){
            entity.setRecipe(recipeRepository.findById(dto.getRecipeId()).get());
        }
        return entity;
    }

    @Override
    public VideoDTO entityToDto(VideoEntity entity) {
        if (entity == null) {
            return null;
        }
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setFilename(entity.getFilename());
        if (entity.getRecipe() != null) {
            dto.setRecipeId(entity.getRecipe().getId());
        }
        return dto;
    }

    @Override
    public List<VideoEntity> dtosToEntities(List<VideoDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VideoDTO> entitiesToDtos(List<VideoEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
