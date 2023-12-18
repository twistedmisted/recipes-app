package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.entity.PhotoEntity;
import ua.kpi.klopotenkoapp.mapper.impl.PhotoMapper;
import ua.kpi.klopotenkoapp.repository.PhotoRepository;
import ua.kpi.klopotenkoapp.service.PhotoService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    @Override
    public void insertPhoto(PhotoDTO photo) {
        log.debug("Saving photo: [{}]", photo);
        if (existsByFilenameAndRecipeId(photo.getFilename(), photo.getRecipeId())) {
            log.warn("The filename already exists. The file has not saved.");
            throw new RuntimeException("The filename already exists for this recipe");
        }
        PhotoEntity photoEntity = photoMapper.dtoToEntity(photo);
        photoRepository.save(photoEntity);
        log.debug("The file was successfully saved");
    }

    private boolean existsByFilenameAndRecipeId(String filename, Long recipeId) {
        return photoRepository.existsByFilenameAndRecipeId(filename, recipeId);
    }
}
