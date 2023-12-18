package ua.kpi.klopotenkoapp.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ua.kpi.klopotenkoapp.contract.util.Complexity;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ComplexityConverter implements AttributeConverter<Complexity, String> {

    @Override
    public String convertToDatabaseColumn(Complexity complexity) {
        if (complexity == null) {
            return null;
        }
        return complexity.getName();
    }

    @Override
    public Complexity convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }
        return Stream.of(Complexity.values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
