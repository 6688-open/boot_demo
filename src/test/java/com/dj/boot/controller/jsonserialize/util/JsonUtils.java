package com.dj.boot.controller.jsonserialize.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName JsonUtils
 * @Description TODO
 * @Author wj
 * @Date 2019/11/20 14:12
 * @Version 1.0
 **/
public class JsonUtils {
    /**
     * ObjectMapper
     */
    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Serialize object to Json string.
     * @param objectMapper ObjectMapper object.
     * @param value Object need to be serialized.
     * @return String Json string.
     * @throws JsonProcessingException From writeValueAsString.
     */
    public static String serialize(ObjectMapper objectMapper, Object value) throws JsonProcessingException {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return (String) value;
        }

        return objectMapper.writeValueAsString(value);
    }

    /**
     * Serialize object to Json string.
     * @param value Object need to be serialized.
     * @return String Json string
     * @throws JsonProcessingException
     */
    public static String serialize(Object value) throws JsonProcessingException {
        return serialize(objectMapper, value);
    }

    /**
     * Deserialize Json string to object.
     * @param objectMapper ObjectMapper object.
     * @param value String need to be deserialized.
     * @param clazz Class object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(ObjectMapper objectMapper, String value, Class<T> clazz) throws IOException {
        if (StringUtils.isNullOrEmpty(value) || clazz == null) {
            return null;
        }

        if (clazz.equals(String.class)) {
            return (T) value;
        }

        return objectMapper.readValue(value, clazz);
    }

    /**
     * Deserialize Json string to object.
     * @param value String need to be deserialized.
     * @param clazz Class object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(String value, Class<T> clazz) throws IOException {
        return deserialize(objectMapper, value, clazz);
    }

    /**
     * Deserialize Json string to object.
     * @param objectMapper ObjectMapper object.
     * @param value String need to be deserialized.
     * @param javaType JavaType object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(ObjectMapper objectMapper, String value, JavaType javaType) throws IOException {
        if (StringUtils.isNullOrEmpty(value)) {
            return null;
        }

        return objectMapper.readValue(value, javaType);
    }

    /**
     * Deserialize Json string to object.
     * @param value String need to be deserialized.
     * @param javaType JavaType object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(String value, JavaType javaType) throws IOException {
        return deserialize(objectMapper, value, javaType);
    }

    /**
     * Deserialize Json string to object.
     * @param objectMapper ObjectMapper object.
     * @param value String need to be deserialized.
     * @param typeReference TypeReference object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(ObjectMapper objectMapper, String value, TypeReference<T> typeReference) throws IOException {
        if (StringUtils.isNullOrEmpty(value) || typeReference == null) {
            return null;
        }

        return objectMapper.readValue(value, typeReference);
    }

    /**
     * Deserialize Json string to object.
     * @param value String need to be deserialized.
     * @param typeReference TypeReference object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(String value, TypeReference<T> typeReference) throws IOException {
        return deserialize(objectMapper, value, typeReference);
    }

    /**
     * Create a JavaType object.
     * @param objectMapper ObjectMapper object.
     * @param collectionClazz Collection Class object.
     * @param elements Element Class object.
     * @return JavaType
     */
    public static JavaType javaType(ObjectMapper objectMapper, Class<?> collectionClazz, Class<?>... elements) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClazz, elements);
    }

    /**
     * Create a JavaType object.
     * @param collectionClazz Collection Class object.
     * @param elements Element Class object.
     * @return JavaType
     */
    public static JavaType javaType(Class<?> collectionClazz, Class<?>... elements) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClazz, elements);
    }

    /**
     * Deserialize Json string to object.
     * @param objectMapper ObjectMapper object.
     * @param value String need to be deserialized.
     * @param collectionClazz Collection Class object.
     * @param elements Element Class object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(ObjectMapper objectMapper, String value, Class<?> collectionClazz, Class<?>... elements) throws IOException {
        JavaType javaType = javaType(objectMapper, collectionClazz, elements);
        return deserialize(objectMapper, value, javaType);
    }

    /**
     * Deserialize Json string to object.
     * @param value String need to be deserialized.
     * @param collectionClazz Collection Class object.
     * @param elements Element Class object.
     * @return {@code T}
     * @throws IOException From readValue.
     */
    public static <T> T deserialize(String value, Class<?> collectionClazz, Class<?>... elements) throws IOException {
        return deserialize(objectMapper, value, collectionClazz, elements);
    }

    /**
     * Deserialize Json string value to List.
     * @param objectMapper ObjectMapper object.
     * @param value Json string value.
     * @param collectionClass Class object for collection.
     * @param elementClass Class object for element in collection.
     * @return {@code List<T>}
     * @throws IOException From readValue.
     */
    public static <T> List<T> deserializeToList(ObjectMapper objectMapper, String value, Class<?> collectionClass, Class<T> elementClass) throws IOException {
        return deserialize(objectMapper, value, collectionClass, elementClass);
    }

    /**
     * Deserialize Json string value to List.
     * @param value Json string value.
     * @param collectionClass Class object for collection.
     * @param elementClass Class object for element in collection.
     * @return {@code List<T>}
     * @throws IOException From readValue.
     */
    public static <T> List<T> deserializeToList(String value, Class<?> collectionClass, Class<T> elementClass) throws IOException {
        return deserializeToList(objectMapper, value, collectionClass, elementClass);
    }

    /**
     * Deserialize jsonParser object to Date object.
     * @param jsonParser JsonParser object.
     * @param dateFormat DateFormat object.
     * @return Date
     * @throws Exception
     */
    public static Date deserializeToDate(JsonParser jsonParser, DateFormat dateFormat) throws Exception {
        String text = jsonParser.getText();
        return dateFormat.parse(text);
    }
}
