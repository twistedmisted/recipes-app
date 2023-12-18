package ua.kpi.klopotenkoapp.contract.filter;

import lombok.Builder;
import lombok.Data;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;

@Data
@Builder
public class RecipeFilter {

    private RecipeStatus status;
    private String region;
    private String category;
    private String name;
}
