//package ua.kpi.klopotenkoapp.entity.converter;
//
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//import ua.kpi.klopotenkoapp.contract.util.Region;
//
//import java.util.stream.Stream;
//
//@Converter(autoApply = true)
//public class RegionConverter implements AttributeConverter<Region, String> {
//
//    @Override
//    public String convertToDatabaseColumn(Region region) {
//        if (region == null) {
//            return null;
//        }
//        return region.getName();
//    }
//
//    @Override
//    public Region convertToEntityAttribute(String name) {
//        if (name == null) {
//            return null;
//        }
//        return Stream.of(Region.values())
//                .filter(n -> n.getName().equals(name))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
//}
