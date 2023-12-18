package ua.kpi.klopotenkoapp.contract.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RatingDTO {

    @JsonIgnore
    private Long id;

    private Double rating = (double) 0;

    private Integer votesNumber = 0;
}
