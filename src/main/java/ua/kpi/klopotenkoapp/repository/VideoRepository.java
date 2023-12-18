package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.entity.VideoEntity;

@Repository
public interface VideoRepository extends CrudRepository<VideoEntity, Long> {

    boolean existsByFilenameAndRecipeId(String filename, Long recipeId);
}
