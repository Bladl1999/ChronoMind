package org.valor.convert;

import org.springframework.core.convert.converter.Converter;
import jakarta.annotation.Nullable;

public class StringToLongConverter implements Converter<String, Long> {

    @Override
    @Nullable
    public Long convert(String source) {
        if (source == null || source.isBlank() || "undefined".equalsIgnoreCase(source) || "null".equalsIgnoreCase(source)) {
            return null;
        }
        try {
            return Long.valueOf(source);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}