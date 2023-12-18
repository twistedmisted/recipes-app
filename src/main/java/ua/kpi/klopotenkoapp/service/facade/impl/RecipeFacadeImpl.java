package ua.kpi.klopotenkoapp.service.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.klopotenkoapp.UnsuccessfulMessageInfo;
import ua.kpi.klopotenkoapp.contract.bo.PageBO;
import ua.kpi.klopotenkoapp.contract.bo.RecipeBO;
import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.dto.SuccessfulMessageInfo;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;
import ua.kpi.klopotenkoapp.contract.filter.RecipeFilter;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;
import ua.kpi.klopotenkoapp.service.CategoryService;
import ua.kpi.klopotenkoapp.service.EmailService;
import ua.kpi.klopotenkoapp.service.PhotoService;
import ua.kpi.klopotenkoapp.service.RecipeService;
import ua.kpi.klopotenkoapp.service.RegionService;
import ua.kpi.klopotenkoapp.service.S3Service;
import ua.kpi.klopotenkoapp.service.UserService;
import ua.kpi.klopotenkoapp.service.VideoService;
import ua.kpi.klopotenkoapp.service.facade.RecipeFacade;

import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@Slf4j
@RequiredArgsConstructor
public class RecipeFacadeImpl implements RecipeFacade {

    private final PhotoService photoService;
    private final VideoService videoService;
    private final RecipeService recipeService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final S3Service s3Service;
    private final UserService userService;
    private final EmailService emailService;
    private final PlatformTransactionManager transactionManager;

    @Override
    public RecipeDTO getRecipeById(long recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    @Override
    public RecipeDTO getRecipeByIdAndStatus(long recipeId, RecipeStatus status) {
        return recipeService.getRecipeByIdAndStatus(recipeId, status);
    }

    @Override
    public RecipeDTO getRecipeForUserById(long recipeId, String email) {
        return recipeService.getRecipeForUserById(recipeId, email);
    }

    @Override
    public PageBO<RecipeDTO> getAllRecipes(int numberOfPage, int sizeOfPage, RecipeFilter recipeFilter) {
        return recipeService.getAllRecipes(numberOfPage, sizeOfPage, recipeFilter);
    }

    @Override
    public void saveRecipe(RecipeBO recipeBO) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        RecipeDTO savedRecipe = null;
        try {
            savedRecipe = insertRecipe(recipeBO.getRecipe());
            setRecipeIdToMedia(savedRecipe.getId(), recipeBO.getPhoto(), recipeBO.getVideo());
            uploadMediaToS3AndInsertToDB(recipeBO.getPhoto(), recipeBO.getVideo());
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            if (!isNull(savedRecipe)) {
                s3Service.removeFilesByRecipeId(savedRecipe.getId());
            }
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Щось пішло не так при збереженні рецепта, спробуйте ще раз.");
        }
    }

    private void uploadMediaToS3AndInsertToDB(PhotoDTO photo, VideoDTO video) {
        s3Service.uploadFiles(photo, video);
        photoService.insertPhoto(photo);
        videoService.insertVideo(video);
    }

    private RecipeDTO insertRecipe(RecipeDTO recipe) {
        recipe.setStatus(RecipeStatus.VALIDATED);
        return recipeService.insertRecipe(recipe);
    }

    private void setRecipeIdToMedia(long recipeId, PhotoDTO photo, VideoDTO video) {
        photo.setRecipeId(recipeId);
        video.setRecipeId(recipeId);
    }

    @Override
    public String getPhotoURLByRecipeId(long recipeId) {
        return s3Service.getPhotoURLByRecipeId(recipeId);
    }

    @Override
    public String getVideoURLByRecipeId(long recipeId) {
        return s3Service.getVideoURLByRecipeId(recipeId);
    }

    @Override
    public List<String> getAllRegions() {
        return regionService.getAllRegions();
    }

    @Override
    public List<String> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Override
    @Transactional
    public void acceptRecipeById(long recipeId) {
        recipeService.acceptRecipeById(recipeId);
        RecipeDTO recipe = getRecipeById(recipeId);
        SuccessfulMessageInfo messageInfo = new SuccessfulMessageInfo();
        messageInfo.setTo(recipe.getUser().getEmail());
        messageInfo.setFullName(recipe.getUser().getSurname() + " " + recipe.getUser().getName());
        messageInfo.setRecipeName(recipe.getName());
        messageInfo.setUrl("kukhovarnyk.ua/recipes/" + recipe.getId());
        emailService.sendRecipeAcceptedMessage(messageInfo);
    }

    @Override
    @Transactional
    public void rejectRecipeById(long recipeId) {
        recipeService.rejectRecipeById(recipeId);
        RecipeDTO recipe = getRecipeById(recipeId);
        UnsuccessfulMessageInfo messageInfo = new UnsuccessfulMessageInfo();
        messageInfo.setTo(recipe.getUser().getEmail());
        messageInfo.setFullName(recipe.getUser().getSurname() + " " + recipe.getUser().getName());
        messageInfo.setRecipeName(recipe.getName());
        emailService.sendRecipeRejectedMessage(messageInfo);
    }
}
