package com.ndgndg91.product.domain;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Slf4j
@Converter
public class ProductMediaConverter implements AttributeConverter<List<Media>, String> {
    @Override
    public String convertToDatabaseColumn(List<Media> attribute) {
        return null;
    }

    @Override
    public List<Media> convertToEntityAttribute(String dbData) {
        return null;
    }
}
