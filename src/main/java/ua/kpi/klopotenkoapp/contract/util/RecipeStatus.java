package ua.kpi.klopotenkoapp.contract.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RecipeStatus {

    @JsonProperty("new")
    NEW,

    @JsonProperty("validated")
    VALIDATED,

    @JsonProperty("verified")
    VERIFIED,

    @JsonProperty("rejected")
    REJECTED,

    @JsonProperty("deleted")
    DELETED,
}
