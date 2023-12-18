package ua.kpi.klopotenkoapp.web.rest.api.v1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.klopotenkoapp.contract.bo.PageBO;
import ua.kpi.klopotenkoapp.contract.bo.RecipeBO;
import ua.kpi.klopotenkoapp.contract.dto.GetRecipe;
import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.contract.dto.RatingDTO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeUserDTO;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;
import ua.kpi.klopotenkoapp.contract.filter.RecipeFilter;
import ua.kpi.klopotenkoapp.contract.util.PhotoExtension;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;
import ua.kpi.klopotenkoapp.contract.util.VideoExtension;
import ua.kpi.klopotenkoapp.exception.IllegalPhotoExtensionException;
import ua.kpi.klopotenkoapp.exception.IllegalVideoExtensionException;
import ua.kpi.klopotenkoapp.service.facade.RecipeFacade;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static ua.kpi.klopotenkoapp.contract.util.RecipeStatus.NEW;
import static ua.kpi.klopotenkoapp.contract.util.RecipeStatus.VALIDATED;
import static ua.kpi.klopotenkoapp.contract.util.RecipeStatus.VERIFIED;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class RecipeController {

    private static final int SIZE_OF_PAGE = 9;
    private static final RecipeStatus VERIFIED_STATUS = VERIFIED;
    private static final RecipeStatus VALIDATED_STATUS = VALIDATED;

    private final RecipeFacade recipeFacade;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRecipeById(@PathVariable(value = "id") long recipeId,
                                                             Principal principal) {
        log.debug("Getting recipe by id = [{}]", recipeId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            log.debug("Getting recipe for admin by id = [{}]", recipeId);
            return new ResponseEntity<>(getRecipeForAdminById(recipeId), OK);
        }
        if (principal == null) {
            return new ResponseEntity<>(getRecipeForGuestById(recipeId), OK);
        }
        return new ResponseEntity<>(getRecipeForUserById(recipeId, principal.getName()), OK);
    }

    private Map<String, Object> getRecipeForUserById(long recipeId, String email) {
        Map<String, Object> response = new HashMap<>();
        RecipeDTO recipeById = getRecipeDTO(recipeId, email);
        String photoURL = getPhotoURLByRecipeId(recipeId);
        String videoURL = getVideoURLByRecipeId(recipeId);
        response.put("recipe", recipeById);
        response.put("photo", photoURL);
        response.put("video", videoURL);
        return response;
    }

    private RecipeDTO getRecipeDTO(long recipeId, String email) {
        RecipeDTO recipeById = recipeFacade.getRecipeForUserById(recipeId, email);
        if (isNull(recipeById)) {
            throw new ResponseStatusException(BAD_REQUEST, "Схоже такого рецепту не існує.");
        }
        return recipeById;
    }

    private Map<String, Object> getRecipeForGuestById(long recipeId) {
        Map<String, Object> response = new HashMap<>();
        RecipeDTO recipeById = getRecipeOrThrowException(recipeFacade.getRecipeByIdAndStatus(recipeId, VERIFIED_STATUS));
        String photoURL = getPhotoURLByRecipeId(recipeId);
        String videoURL = getVideoURLByRecipeId(recipeId);
        response.put("recipe", recipeById);
        response.put("photo", photoURL);
        response.put("video", videoURL);
        return response;
    }

    private RecipeDTO getRecipeOrThrowException(RecipeDTO recipe) {
        if (isNull(recipe)) {
            throw new ResponseStatusException(BAD_REQUEST, "Схоже такого рецепту не існує.");
        }
        return recipe;
    }

    private String getVideoURLByRecipeId(long recipeId) {
        try {
            return recipeFacade.getVideoURLByRecipeId(recipeId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Не вдалося знайти відео для рецепту.");
        }
    }

    private String getPhotoURLByRecipeId(long recipeId) {
        try {
            return recipeFacade.getPhotoURLByRecipeId(recipeId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Не вдалося знайти фото для рецепту.");
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVerifiedRecipes(
            @RequestParam(value = "page", required = false, defaultValue = "1") int numberOfPage,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "name", required = false) String name) {
        log.debug("Getting all recipes");
        RecipeFilter recipeFilter = RecipeFilter.builder()
                .status(VERIFIED_STATUS)
                .region(region)
                .category(category)
                .name(name)
                .build();
        PageBO<GetRecipe> page = createRecipePage(numberOfPage, recipeFilter);
        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        return new ResponseEntity<>(response, OK);
    }

    private Map<String, Object> getRecipeForAdminById(long recipeId) {
        Map<String, Object> response = new HashMap<>();
        RecipeDTO recipeById = getRecipeOrThrowException(recipeFacade.getRecipeById(recipeId));
        String photoURL = getPhotoURLByRecipeId(recipeId);
        String videoURL = getVideoURLByRecipeId(recipeId);
        response.put("recipe", recipeById);
        response.put("photo", photoURL);
        response.put("video", videoURL);
        return response;
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getRecipes(
            @RequestParam(value = "page", required = false, defaultValue = "1") int numberOfPage) {
        log.debug("Getting all for admin recipes");
        PageBO<GetRecipe> page = createRecipePage(numberOfPage, RecipeFilter.builder().status(VALIDATED_STATUS).build());
        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        return new ResponseEntity<>(response, OK);
    }

    private PageBO<GetRecipe> createRecipePage(int numberOfPage, RecipeFilter recipeFilter) {
        PageBO<RecipeDTO> recipes = getRecipePage(numberOfPage, recipeFilter);
        PageBO<GetRecipe> page = new PageBO<>();
        page.setTotalPages(recipes.getTotalPages());
        page.setCurrentPageNumber(recipes.getCurrentPageNumber() + 1);
        page.setContent(parseContent(recipes.getContent()));
        return page;
    }

    private List<GetRecipe> parseContent(List<RecipeDTO> recipes) {
        List<GetRecipe> content = new ArrayList<>();
        for (RecipeDTO recipe : recipes) {
            GetRecipe getRecipe = new GetRecipe();
            getRecipe.setRecipe(recipe);
            getRecipe.setPhotoURL(getPhotoURLByRecipeId(recipe.getId()));
            getRecipe.setVideoURL(getVideoURLByRecipeId(recipe.getId()));
            content.add(getRecipe);
        }
        return content;
    }

    private PageBO<RecipeDTO> getRecipePage(int numberOfPage, RecipeFilter recipeFilter) {
        PageBO<RecipeDTO> recipes = recipeFacade.getAllRecipes(numberOfPage, SIZE_OF_PAGE, recipeFilter);
        if (!recipes.hasContent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Схоже ще ніхто не додав рецепти. Будьте першими!");
        }
        return recipes;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> saveRecipe(@Valid @RequestPart(value = "recipe") RecipeDTO recipeDTO,
                                                          @RequestPart(value = "photo", required = false) MultipartFile photo,
                                                          @RequestPart(value = "video", required = false) MultipartFile video,
                                                          Principal principal) {
        log.debug("Saving recipe: [{}]", recipeDTO);
        checkTimeAndIfNotExistsThrowException(recipeDTO.getHours(), recipeDTO.getMinutes());
        recipeDTO.setStatus(NEW);
        Map<String, Object> response = new HashMap<>();
        try {
            recipeFacade.saveRecipe(createRecipeBO(recipeDTO, photo, video, principal.getName()));
            response.put("message", "The recipe was successfully saved. Wait for moderation and response.");
        } catch (IllegalPhotoExtensionException e) {
            log.warn("The photo file extension is not allowed", e);
            response.put("message", "Photo extension is not allowed. Photo extensions available: .png, .jpg, .jpeg");
        } catch (IllegalVideoExtensionException e) {
            log.warn("The video file extension is not allowed", e);
            response.put("message", "Video extension is not allowed. Video extensions available: .mp4, .wmv, .avi, .mkv");
        }
        return new ResponseEntity<>(response, CREATED);
    }

    private void checkTimeAndIfNotExistsThrowException(Integer hours, Integer minutes) {
        if (hours <= 0 && minutes <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "Введіть час для приготування страви.");
        }
    }

    private RecipeBO createRecipeBO(RecipeDTO recipeDTO, MultipartFile photo, MultipartFile video, String email) {
        RecipeBO recipeBO = new RecipeBO();
        addRatingToRecipe(recipeDTO);
        recipeBO.setRecipe(recipeDTO);
        recipeBO.getRecipe().setUser(new RecipeUserDTO(email));
        recipeBO.setPhoto(parseToPhotoDTO(photo));
        recipeBO.setVideo(parseToVideoDTO(video));
        return recipeBO;
    }

    private void addRatingToRecipe(RecipeDTO recipeDTO) {
//        RatingDTO rating = new RatingDTO();
//        rating.setRating((double) 0L);
//        rating.setVotesNumber(0);
        recipeDTO.setRating(new RatingDTO());
    }

    private VideoDTO parseToVideoDTO(MultipartFile video) {
        final int videoNumber = 1;
        String originalFilename = video.getOriginalFilename();
        if (!checkVideoFileExtension(video.getContentType())) {
            throw new IllegalVideoExtensionException("File extension for '" + originalFilename + "' video is not allowed");
        }
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setFilename(videoNumber + originalFilename.substring(originalFilename.indexOf('.')));
        try {
            videoDTO.setInputStream(video.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot get input stream from video");
        }
        return videoDTO;
    }

    private boolean checkVideoFileExtension(String contentType) {
        return VideoExtension.exists(contentType);
    }

    private PhotoDTO parseToPhotoDTO(MultipartFile photo) {
        final int photoNumber = 1;
        String originalFilename = photo.getOriginalFilename();
        if (!checkPhotoFileExtension(photo.getContentType())) {
            throw new IllegalPhotoExtensionException("File extension '" + originalFilename + "' is not allowed");
        }
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setFilename(photoNumber + originalFilename.substring(originalFilename.indexOf('.')));
        try {
            photoDTO.setInputStream(photo.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot get input stream from photo");
        }
        return photoDTO;
    }

    private boolean checkPhotoFileExtension(String contentType) {
        return PhotoExtension.exists(contentType);
    }

    @PostMapping("/{id}/accept")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> acceptRecipe(@PathVariable(value = "id") long recipeId) {
        log.debug("Accepting recipe with id = [{}]", recipeId);
        recipeFacade.acceptRecipeById(recipeId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "The recipe was successfully verified");
        return new ResponseEntity<>(response, OK);
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> rejectRecipe(@PathVariable(value = "id") long recipeId) {
        log.debug("Accepting recipe with id = [{}]", recipeId);
        recipeFacade.rejectRecipeById(recipeId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "The recipe was successfully rejected");
        return new ResponseEntity<>(response, OK);
    }
}
