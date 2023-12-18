package ua.kpi.klopotenkoapp.service;

import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;

public interface S3Service {

    void uploadFiles(PhotoDTO photo, VideoDTO video);

    String getPhotoURLByRecipeId(long recipeId);

    String getVideoURLByRecipeId(long recipeId);

    void removeFilesByRecipeId(long recipeId);
}
