package com.ndgndg91.product.domain;

import javax.persistence.AttributeConverter;
import java.util.List;

public class OptionNameConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return null;
    }
}
