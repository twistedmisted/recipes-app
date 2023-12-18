package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    @Query("SELECT u.email FROM UserEntity u INNER JOIN RecipeEntity r WHERE r.id = :recipeId")
    String findUserEmailByRecipeId(long recipeId);
}
