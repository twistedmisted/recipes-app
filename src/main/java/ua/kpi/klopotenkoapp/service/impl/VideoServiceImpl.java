package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;
import ua.kpi.klopotenkoapp.entity.VideoEntity;
import ua.kpi.klopotenkoapp.mapper.impl.VideoMapper;
import ua.kpi.klopotenkoapp.repository.VideoRepository;
import ua.kpi.klopotenkoapp.service.VideoService;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    public void insertVideo(VideoDTO video) {
        log.debug("Saving video: [{}]", video);
        if (existsByFilenameAndRecipeId(video.getFilename(), video.getRecipeId())) {
            log.warn("The filename already exists. The file has not saved.");
            return;
        }
        VideoEntity videoEntity = videoMapper.dtoToEntity(video);
        videoRepository.save(videoEntity);
        log.debug("The file was successfully saved");
    }

    private boolean existsByFilenameAndRecipeId(String filename, Long recipeId) {
        return videoRepository.existsByFilenameAndRecipeId(filename, recipeId);
    }
}
