package ua.kpi.klopotenkoapp.service;

import ua.kpi.klopotenkoapp.contract.bo.PageBO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.filter.RecipeFilter;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;

public interface RecipeService {

    RecipeDTO getRecipeById(long recipeId);

    RecipeDTO getRecipeByIdAndStatus(long recipeId, RecipeStatus status);

    RecipeDTO getRecipeForUserById(long recipeId, String email);

    RecipeDTO insertRecipe(RecipeDTO recipeDTO);

    PageBO<RecipeDTO> getAllRecipes(int numberOfPage, int sizeOfPage, RecipeFilter recipeFilter);

    void acceptRecipeById(long recipeId);

    void rejectRecipeById(long recipeId);
}
