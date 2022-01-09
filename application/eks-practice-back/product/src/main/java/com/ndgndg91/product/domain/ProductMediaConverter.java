package com.ndgndg91.product.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ndgndg91.product.global.JsonConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class ProductMediaConverter implements AttributeConverter<List<Media>, String> {
    @Override
    public String convertToDatabaseColumn(List<Media> attribute) {
        return JsonConverter.toJson(attribute);
    }

    @Override
    public List<Media> convertToEntityAttribute(String dbData) {
        return JsonConverter.fromJson(dbData, new TypeReference<>() {});
    }
}
