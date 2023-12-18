package ua.kpi.klopotenkoapp.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeUserDTO;
import ua.kpi.klopotenkoapp.entity.RecipeEntity;
import ua.kpi.klopotenkoapp.entity.UserEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;
import ua.kpi.klopotenkoapp.repository.CategoryRepository;
import ua.kpi.klopotenkoapp.repository.RegionRepository;
import ua.kpi.klopotenkoapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeMapper implements Mapper<RecipeEntity, RecipeDTO> {

    private final RegionRepository regionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RatingMapper ratingMapper;

    @Override
    public RecipeEntity dtoToEntity(RecipeDTO dto) {
        if (dto == null) {
            return null;
        }
        RecipeEntity entity = new RecipeEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setIngredients(dto.getIngredients());
        entity.setSteps(dto.getSteps());
        entity.setStatus(dto.getStatus());
        entity.setRegion(regionRepository.findByName(dto.getRegion()).get());
        entity.setCategory(categoryRepository.findByName(dto.getCategory()).get());
        entity.setHours(dto.getHours());
        entity.setMinutes(dto.getMinutes());
        entity.setComplexity(dto.getComplexity());
        entity.setRating(ratingMapper.dtoToEntity(dto.getRating()));
        entity.setUser(userRepository.findByEmail(dto.getUser().getEmail()).get());
        return entity;
    }

    @Override
    public RecipeDTO entityToDto(RecipeEntity entity) {
        if (entity == null) {
            return null;
        }
        RecipeDTO dto = new RecipeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIngredients(entity.getIngredients());
        dto.setSteps(entity.getSteps());
        dto.setStatus(entity.getStatus());
        dto.setRegion(entity.getRegion().getName());
        dto.setCategory(entity.getCategory().getName());
        dto.setHours(entity.getHours());
        dto.setMinutes(entity.getMinutes());
        dto.setComplexity(entity.getComplexity());
        dto.setRating(ratingMapper.entityToDto(entity.getRating()));
        dto.setUser(userEntityToRecipeUserDto(entity.getUser()));
        return dto;
    }

    private RecipeUserDTO userEntityToRecipeUserDto(UserEntity user) {
        if (user == null) {
            return null;
        }
        RecipeUserDTO dto = new RecipeUserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public List<RecipeEntity> dtosToEntities(List<RecipeDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<RecipeEntity> entities = new ArrayList<>();
        for (RecipeDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

    @Override
    public List<RecipeDTO> entitiesToDtos(List<RecipeEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<RecipeDTO> dtos = new ArrayList<>();
        for (RecipeEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
