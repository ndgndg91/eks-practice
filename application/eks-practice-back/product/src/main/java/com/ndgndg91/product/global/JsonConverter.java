package com.ndgndg91.product.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonConverter {
    private static final ObjectMapper om = new ObjectMapper();

    public static <T> String toJson(T data) {
        try {
            return om.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert {} to Json String ", data, e);
            return null;
        }
    }

    public static <T> T fromJson(final String json, final TypeReference<T> type) {
        try {
            return om.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert {} to {}", json, type, e);
            return null;
        }
    }
}
