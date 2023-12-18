package ua.kpi.klopotenkoapp.entity.specification;

import org.springframework.data.jpa.domain.Specification;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;
import ua.kpi.klopotenkoapp.entity.RecipeEntity;

import static jakarta.persistence.criteria.JoinType.LEFT;

public class RecipeSpecification {

    public static Specification<RecipeEntity> hasStatus(RecipeStatus status) {
        if (status == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<RecipeEntity> hasRegion(String region) {
        if (region == null || region.isBlank()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("region", LEFT).get("name"), region);
    }

    public static Specification<RecipeEntity> hasCategory(String category) {
        if (category == null || category.isBlank()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("category", LEFT).get("name"), category);
    }

    public static Specification<RecipeEntity> containsInName(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name").as(String.class), "%" + text + "%");
    }
}
