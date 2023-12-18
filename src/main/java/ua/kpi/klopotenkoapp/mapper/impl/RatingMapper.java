package ua.kpi.klopotenkoapp.mapper.impl;

import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.RatingDTO;
import ua.kpi.klopotenkoapp.entity.RatingEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;

import java.util.List;

@Component
public class RatingMapper implements Mapper<RatingEntity, RatingDTO> {

    @Override
    public RatingEntity dtoToEntity(RatingDTO dto) {
        if (dto == null) {
            return null;
        }
        RatingEntity entity = new RatingEntity();
        entity.setId(dto.getId());
        entity.setRating(dto.getRating());
        entity.setVotesNumber(dto.getVotesNumber());
        return entity;
    }

    @Override
    public RatingDTO entityToDto(RatingEntity entity) {
        if (entity == null) {
            return null;
        }
        RatingDTO dto = new RatingDTO();
        dto.setId(entity.getId());
        dto.setRating(entity.getRating());
        dto.setVotesNumber(entity.getVotesNumber());
        return dto;
    }

    @Override
    public List<RatingEntity> dtosToEntities(List<RatingDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RatingDTO> entitiesToDtos(List<RatingEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
