package ua.kpi.klopotenkoapp.contract.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Region {

    @JsonProperty("Бессарабія")
    BESSARABIA("Бессарабія"),

    @JsonProperty("Буковина")
    BUKOVYNA("Буковина"),

    @JsonProperty("Донеччина")
    DONECHCHYNA("Донеччина"),

    @JsonProperty("Галичина")
    HALYCHYNA("Галичина"),

    @JsonProperty("Київ")
    KYIV("Київ"),

    @JsonProperty("Наддніпрянщина")
    NADDNIPRIANSHCHYNA("Наддніпрянщина"),

    @JsonProperty("Полісся")
    POLISSIA("Полісся"),

    @JsonProperty("Полтавщина")
    POLTAVSHCHYNA("Полтавщина"),

    @JsonProperty("Приазов`я")
    PRYAZOVIA("Приазов`я"),

    @JsonProperty("Причорномор`я")
    PRYCHORNOMORIA("Причорномор`я"),

    @JsonProperty("Сіверщина")
    SIVERSHCHYNA("Сіверщина"),

    @JsonProperty("Слобожанщина")
    SLOBOZHANSHCHYNA("Слобожанщина"),

    @JsonProperty("Таврія")
    TAVRIA("Таврія"),

    @JsonProperty("Карпати")
    THE_CARPATHIANS("Карпати"),

    @JsonProperty("Волинь")
    VOLYN("Волинь"),

    @JsonProperty("Закарпаття")
    ZAKARPATTIA("Закарпаття"),

    @JsonIgnore
    EMPTY("empty")
    ;

    private static final Region[] REGIONS = values();

    private final String name;

    Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Region getRegionByName(String name) {
        for (Region region : REGIONS) {
            if (region.getName().equals(name)) {
                return region;
            }
        }
        return Region.EMPTY;
    }
}
