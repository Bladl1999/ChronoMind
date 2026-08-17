package org.valor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.valor.convert.StringToEnumConverterFactory;
import org.valor.convert.StringToLocalDateConverter;
import org.valor.convert.StringToLongConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new StringToLongConverter());
        registry.addConverter(new StringToLocalDateConverter());
    }
}