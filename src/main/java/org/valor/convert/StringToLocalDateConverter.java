package org.valor.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    @Nullable
    public LocalDate convert(String source) {
        if (source == null || source.isBlank() || "undefined".equalsIgnoreCase(source) || "null".equalsIgnoreCase(source)) {
            return null;
        }
        try {
            return LocalDate.parse(source); // ожидается ISO-формат yyyy-MM-dd
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
