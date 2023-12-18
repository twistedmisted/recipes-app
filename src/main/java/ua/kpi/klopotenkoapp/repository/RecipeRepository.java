package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;
import ua.kpi.klopotenkoapp.entity.RecipeEntity;

import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {

    Optional<RecipeEntity> findByIdAndStatus(Long id, RecipeStatus status);

    Page<RecipeEntity> findAll(Specification<RecipeEntity> specification, Pageable pageable);

    @Modifying
    @Query("UPDATE RecipeEntity r SET r.status = :status WHERE r.id = :id")
    void updateRecipeStatusById(@Param("id") Long recipeId, @Param("status") RecipeStatus recipeStatus);
}
