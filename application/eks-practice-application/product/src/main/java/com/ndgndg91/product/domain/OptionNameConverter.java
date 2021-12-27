package com.ndgndg91.product.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ndgndg91.product.global.JsonConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class OptionNameConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return JsonConverter.toJson(attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return JsonConverter.fromJson(dbData, new TypeReference<>() {});
    }
}
