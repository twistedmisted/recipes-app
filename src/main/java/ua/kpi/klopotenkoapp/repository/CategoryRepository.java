package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.entity.CategoryEntity;
import ua.kpi.klopotenkoapp.repository.projection.CategoryNameOnly;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByName(String category);

    @Query(value = "SELECT c.name as name FROM CategoryEntity c")
    List<CategoryNameOnly> findProjections();
}
