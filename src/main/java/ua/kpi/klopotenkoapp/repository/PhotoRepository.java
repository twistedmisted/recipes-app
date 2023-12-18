package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.entity.PhotoEntity;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Long> {

    boolean existsByFilenameAndRecipeId(String filename, Long recipeId);
}
