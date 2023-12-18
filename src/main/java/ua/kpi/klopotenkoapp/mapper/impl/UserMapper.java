package ua.kpi.klopotenkoapp.mapper.impl;

import org.springframework.stereotype.Component;
import ua.kpi.klopotenkoapp.contract.dto.UserDTO;
import ua.kpi.klopotenkoapp.entity.UserEntity;
import ua.kpi.klopotenkoapp.mapper.Mapper;

import java.util.List;

@Component
public class UserMapper implements Mapper<UserEntity, UserDTO> {

    @Override
    public UserEntity dtoToEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setVerified(dto.getVerified());
        return entity;
    }

    @Override
    public UserDTO entityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setVerified(entity.getVerified());
        return dto;
    }

    @Override
    public List<UserEntity> dtosToEntities(List<UserDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserDTO> entitiesToDtos(List<UserEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
