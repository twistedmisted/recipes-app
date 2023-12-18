package ua.kpi.klopotenkoapp.service.facade;

import ua.kpi.klopotenkoapp.contract.bo.PageBO;
import ua.kpi.klopotenkoapp.contract.bo.RecipeBO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.filter.RecipeFilter;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;

import java.util.List;

public interface RecipeFacade {

    RecipeDTO getRecipeById(long recipeId);

    RecipeDTO getRecipeByIdAndStatus(long recipeId, RecipeStatus status);

    RecipeDTO getRecipeForUserById(long recipeId, String email);

    PageBO<RecipeDTO> getAllRecipes(int numberOfPage, int sizeOfPage, RecipeFilter recipeFilter);

    void saveRecipe(RecipeBO recipeBO);

    String getPhotoURLByRecipeId(long recipeId);

    String getVideoURLByRecipeId(long recipeId);

    List<String> getAllRegions();

    List<String> getAllCategories();

    void acceptRecipeById(long recipeId);

    void rejectRecipeById(long recipeId);
}
