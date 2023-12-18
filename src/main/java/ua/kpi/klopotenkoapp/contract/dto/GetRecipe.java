package ua.kpi.klopotenkoapp.contract.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "recipe")
public class GetRecipe {

    private RecipeDTO recipe;

    @JsonProperty(value = "photo")
    private String photoURL;

    @JsonProperty(value = "video")
    private String videoURL;
}
