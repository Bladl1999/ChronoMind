package org.valor.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @Override
    public <R extends Enum<?>> Converter<String, R> getConverter(Class<R> targetType) {
        return source -> {
            if (source == null || source.isBlank() ||
                    "undefined".equalsIgnoreCase(source) ||
                    "null".equalsIgnoreCase(source)) {
                return null;
            }
            try {
                @SuppressWarnings("unchecked")
                Class<? extends Enum> rawType = (Class<? extends Enum>) targetType;
                return targetType.cast(Enum.valueOf(rawType, source.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return null; // или выбросить исключение, если нужна строгая проверка
            }
        };
    }
}