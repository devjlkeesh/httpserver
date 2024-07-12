package dev.jlkeesh.httpserver.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.nio.charset.StandardCharsets;

public final class GsonUtil {
    @Getter
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private GsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String objectToJson(Object obj) {
        return gson.toJson(obj);
    }

    public static byte[] jsonStringToByteArray(String jsonString) {
        return jsonString.getBytes();
    }

    public static byte[] objectToByteArray(Object obj) {
        return objectToJson(obj).getBytes(StandardCharsets.UTF_8    );
    }
}
