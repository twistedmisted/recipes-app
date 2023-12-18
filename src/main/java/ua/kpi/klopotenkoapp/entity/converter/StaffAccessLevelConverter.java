package ua.kpi.klopotenkoapp.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ua.kpi.klopotenkoapp.contract.util.StaffAccessLevel;

@Converter(autoApply = true)
public class StaffAccessLevelConverter implements AttributeConverter<StaffAccessLevel, String> {

    @Override
    public String convertToDatabaseColumn(StaffAccessLevel staffAccessLevel) {
        if (staffAccessLevel == null) {
            return null;
        }
        return staffAccessLevel.name();
    }

    @Override
    public StaffAccessLevel convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return StaffAccessLevel.valueOf(s);
    }
}
