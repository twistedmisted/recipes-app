package ua.kpi.klopotenkoapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.kpi.klopotenkoapp.contract.util.StringRecipeStatusToEnumConverter;
import ua.kpi.klopotenkoapp.entity.converter.StringComplexityToEnumConverter;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*");
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringRecipeStatusToEnumConverter());
        registry.addConverter(new StringComplexityToEnumConverter());
    }
}
