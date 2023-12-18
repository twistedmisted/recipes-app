package ua.kpi.klopotenkoapp.contract.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhotoDTO extends MediaDTO {

    private Long recipeId;
}
