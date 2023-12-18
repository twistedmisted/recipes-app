package ua.kpi.klopotenkoapp.contract.util;

import org.springframework.core.convert.converter.Converter;

public class StringRecipeStatusToEnumConverter implements Converter<String, RecipeStatus> {

    @Override
    public RecipeStatus convert(String source) {
        return RecipeStatus.valueOf(source.toUpperCase());
    }
}
