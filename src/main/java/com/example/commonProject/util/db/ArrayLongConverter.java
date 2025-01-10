package com.example.commonProject.util.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Converter
public class ArrayLongConverter implements AttributeConverter<List<Long>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        return attribute.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(dbData.split(DELIMITER))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
