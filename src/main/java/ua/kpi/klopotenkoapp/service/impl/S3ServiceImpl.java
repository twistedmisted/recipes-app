package ua.kpi.klopotenkoapp.service.impl;

import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.client.S3Client;
import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;
import ua.kpi.klopotenkoapp.service.S3Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private static final String BUCKET_NAME = "recipes-media";
    private static final String PHOTO = "photo";
    private static final String VIDEO = "video";
    private static final String SLASH = "/";

    private final S3Client s3Client;

    @PostConstruct
    public void createBucket() {
        log.debug("Check if bucket with name [{}] exists", BUCKET_NAME);
        if (!s3Client.getAmazonS3().doesBucketExistV2(BUCKET_NAME)) {
            log.debug("Creating bucket with name [{}]", BUCKET_NAME);
            s3Client.getAmazonS3().createBucket(BUCKET_NAME);
            log.debug("The bucket created successfully");
        }
    }

    @Override
    public void uploadFiles(PhotoDTO photo, VideoDTO video) {
        log.debug("Uploading files to S3");
        savePhoto(photo);
        saveVideo(video);
        log.debug("The files were successfully uploaded");
    }

    private void saveVideo(VideoDTO video) {
        s3Client.getAmazonS3().putObject(
                BUCKET_NAME,
                video.getRecipeId() + SLASH + VIDEO + SLASH + video.getFilename(),
                video.getInputStream(),
                new ObjectMetadata()
        );
    }

    private void savePhoto(PhotoDTO photo) {
        s3Client.getAmazonS3().putObject(
                BUCKET_NAME,
                photo.getRecipeId() + SLASH + PHOTO + SLASH + photo.getFilename(),
                photo.getInputStream(),
                new ObjectMetadata()
        );
    }

    @Override
    public String getPhotoURLByRecipeId(long recipeId) {
        List<String> keys = findAllKeysByPrefix(recipeId + SLASH + PHOTO + SLASH);
        if (keys.isEmpty()) {
            log.warn("Cannot find photos for recipe with id = [{}]", recipeId);
            throw new IllegalArgumentException("Cannot find photos for recipe with id = [" + recipeId + "]");
        }
        String key = keys.get(0);
        return s3Client.getAmazonS3().getUrl(BUCKET_NAME, key).toString();
    }

    @Override
    public String getVideoURLByRecipeId(long recipeId) {
        List<String> keys = findAllKeysByPrefix(recipeId + SLASH + VIDEO + SLASH);
        if (keys.isEmpty()) {
            log.warn("Cannot find videos for recipe with id = [{}]", recipeId);
            throw new IllegalArgumentException("Cannot find videos for recipe with id = [" + recipeId + "]");
        }
        String key = keys.get(0);
        return s3Client.getAmazonS3().getUrl(BUCKET_NAME, key).toString();
    }

    private List<String> findAllKeysByPrefix(String prefix) {
        return s3Client.getAmazonS3().listObjects(BUCKET_NAME, prefix).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .toList();
    }

    @Override
    public void removeFilesByRecipeId(long recipeId) {
        log.debug("Removing files from S3 by recipeId = [{}]", recipeId);
        List<String> keysToDelete = findAllKeysByPrefix(recipeId + SLASH);
        if (keysToDelete.isEmpty()) {
            log.debug("The files do not exist for recipeId = [{}]", recipeId);
            return;
        }
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(BUCKET_NAME)
                .withKeys(keysToDelete.toArray(new String[0]));
        s3Client.getAmazonS3().deleteObjects(deleteObjectsRequest);
        log.debug("The files were removed successfully");
    }
}
