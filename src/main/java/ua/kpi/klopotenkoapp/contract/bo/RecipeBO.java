package ua.kpi.klopotenkoapp.contract.bo;

import lombok.Data;
import ua.kpi.klopotenkoapp.contract.dto.PhotoDTO;
import ua.kpi.klopotenkoapp.contract.dto.RecipeDTO;
import ua.kpi.klopotenkoapp.contract.dto.VideoDTO;

@Data
public class RecipeBO {

    private RecipeDTO recipe;
    private PhotoDTO photo;
    private VideoDTO video;
}
