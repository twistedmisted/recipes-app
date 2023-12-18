package ua.kpi.klopotenkoapp.entity.converter;

import org.springframework.core.convert.converter.Converter;
import ua.kpi.klopotenkoapp.contract.util.Complexity;

public class StringComplexityToEnumConverter implements Converter<String, Complexity> {

    @Override
    public Complexity convert(String source) {
        return Complexity.getComplexityByName(source);
    }
}
