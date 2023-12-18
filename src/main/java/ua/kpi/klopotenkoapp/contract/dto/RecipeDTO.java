package ua.kpi.klopotenkoapp.contract.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ua.kpi.klopotenkoapp.contract.util.Complexity;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;

import java.util.List;

@Data
@JsonRootName(value = "recipe")
public class RecipeDTO {

    private Long id;

    @NotBlank(message = "Введіть назву рецепту.")
    @Size(min = 5, max = 255, message = "Назва рецепту має бути від 5 до 255 символів.")
    private String name;

    @NotBlank(message = "Введіть опис до рецепту.")
    private String description;

    @NotEmpty(message = "Введіть потрібні інгредієнти до рецепту.")
    private List<String> ingredients;

    @NotEmpty(message = "Введіть кроки для приготування.")
    private List<String> steps;

    @NotNull(message = "Оберіть регіон походження страви.")
    private String region;

    @NotNull(message = "Оберіть категорію для страви.")
    private String category;

    @NotNull(message = "Оберіть складність приготування страви.")
    private Complexity complexity;

    private Integer hours;

    private Integer minutes;

    private RatingDTO rating;

    private RecipeUserDTO user;

    private RecipeStatus status;
}
