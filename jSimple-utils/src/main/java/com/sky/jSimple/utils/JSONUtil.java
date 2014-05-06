package com.sky.jSimple.utils;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {


    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static
    {
    	objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    // 将 Java 对象转为 JSON 字符串
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    // 将 JSON 字符串转为 Java 对象
    public static <T> T fromJSON(String json, Class<T> type) {
        T obj;
        try {
            obj = objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
