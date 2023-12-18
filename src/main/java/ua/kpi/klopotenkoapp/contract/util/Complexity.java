package ua.kpi.klopotenkoapp.contract.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Complexity {

    @JsonProperty("Легкий")
    EASY("Легкий"),

    @JsonProperty("Помірний")
    MEDIUM("Помірний"),

    @JsonProperty("Складний")
    HARD("Складний"),

    @JsonIgnore
    EMPTY("empty"),
    ;

    private static final Complexity[] COMPLEXITIES = values();

    private final String name;

    Complexity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Complexity getComplexityByName(String name) {
        for (Complexity complexity : COMPLEXITIES) {
            if (complexity.getName().equals(name)) {
                return complexity;
            }
        }
        return Complexity.EMPTY;
    }
}
