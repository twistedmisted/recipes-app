package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.contract.bo.PageBO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.filter.RecipeFilter;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;
import ua.kpi.klopotenkoapp.entity.RecipeEntity;
import ua.kpi.klopotenkoapp.mapper.impl.RecipeMapper;
import ua.kpi.klopotenkoapp.repository.RecipeRepository;
import ua.kpi.klopotenkoapp.service.RecipeService;

import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.data.jpa.domain.Specification.where;
import static ua.kpi.klopotenkoapp.contract.util.RecipeStatus.REJECTED;
import static ua.kpi.klopotenkoapp.contract.util.RecipeStatus.VALIDATED;
import static ua.kpi.klopotenkoapp.contract.util.RecipeStatus.VERIFIED;
import static ua.kpi.klopotenkoapp.entity.specification.RecipeSpecification.containsInName;
import static ua.kpi.klopotenkoapp.entity.specification.RecipeSpecification.hasCategory;
import static ua.kpi.klopotenkoapp.entity.specification.RecipeSpecification.hasRegion;
import static ua.kpi.klopotenkoapp.entity.specification.RecipeSpecification.hasStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private static final RecipeStatus VALIDATED_STATUS = VALIDATED;
    private static final RecipeStatus REJECTED_STATUS = REJECTED;

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public RecipeDTO getRecipeById(long recipeId) {
        log.debug("Getting recipe by id = [{}]", recipeId);
        return recipeMapper.entityToDto(recipeRepository.findById(recipeId).orElse(null));
    }

    @Override
    public RecipeDTO getRecipeByIdAndStatus(long recipeId, RecipeStatus status) {
        log.debug("Getting recipe by id = [{}] and status = [{}]", recipeId, status);
        return recipeMapper.entityToDto(recipeRepository.findByIdAndStatus(recipeId, status).orElse(null));
    }

    @Override
    public RecipeDTO getRecipeForUserById(long recipeId, String email) {
        log.debug("Getting recipe by id = [{}] for authenticated user", recipeId);
        RecipeEntity recipeEntity = recipeRepository.findById(recipeId).orElse(null);
        if (isNull(recipeEntity)) {
            log.info("Cannot find a recipe by id = [{}]", recipeId);
            return null;
        }
        if (hasVerifiedStatus(recipeEntity) || isAuthor(email, recipeEntity)) {
            return recipeMapper.entityToDto(recipeEntity);
        }
        return null;
    }

    private static boolean hasVerifiedStatus(RecipeEntity recipeEntity) {
        return VERIFIED.equals(recipeEntity.getStatus());
    }

    private static boolean isAuthor(String email, RecipeEntity recipeEntity) {
        return email.equals(recipeEntity.getUser().getEmail());
    }

    @Override
    public RecipeDTO insertRecipe(RecipeDTO recipeDTO) {
        log.debug("Inserting recipe: [{}]", recipeDTO);
        RecipeEntity recipeEntity = recipeMapper.dtoToEntity(recipeDTO);
        recipeEntity.getRating().setRecipe(recipeEntity);
        recipeEntity = recipeRepository.save(recipeEntity);
        log.debug("Successfully inserted the recipe");
        return recipeMapper.entityToDto(recipeEntity);
    }

    @Override
    public PageBO<RecipeDTO> getAllRecipes(int numberOfPage, int sizeOfPage, RecipeFilter recipeFilter) {
        log.debug("Getting all recipes by numberOfPage = [{}] and sizeOfPage = [{}]", numberOfPage, sizeOfPage);
        if (lessThanOne(numberOfPage) && lessThanOne(sizeOfPage)) {
            log.warn("The number of page and size of page must be greater than zero");
            throw new IllegalArgumentException("The number of page and size of page must be greater than zero");
        }
        Page<RecipeEntity> recipesPage = recipeRepository.findAll(where(hasStatus(recipeFilter.getStatus()))
                        .and(hasRegion(recipeFilter.getRegion()))
                        .and(hasCategory(recipeFilter.getCategory()))
                        .and(containsInName(recipeFilter.getName())),
                PageRequest.of(numberOfPage - 1, sizeOfPage));
        if (!recipesPage.hasContent()) {
            log.info("Cannot find recipes by status verified");
            return new PageBO<>();
        }
        List<RecipeDTO> recipeDTOS = recipesPage.getContent().stream()
                .map(recipeMapper::entityToDto)
                .toList();
        return new PageBO<>(recipeDTOS, recipesPage.getNumber(), recipesPage.getTotalPages());
    }

    private boolean lessThanOne(int number) {
        return number <= 0;
    }

    @Override
    public void acceptRecipeById(long recipeId) {
        log.debug("Accepting recipe with id = [{}]", recipeId);
        recipeRepository.updateRecipeStatusById(recipeId, VALIDATED_STATUS);
    }

    @Override
    public void rejectRecipeById(long recipeId) {
        log.debug("Accepting recipe with id = [{}]", recipeId);
        recipeRepository.updateRecipeStatusById(recipeId, REJECTED_STATUS);
    }
}
