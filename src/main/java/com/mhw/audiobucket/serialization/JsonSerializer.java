package com.mhw.audiobucket.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mxw4182 on 12/16/16.
 */
public class JsonSerializer {

    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    public static String json(Object o) {
        return gson.toJson(o);
    }

    public static Object toObject(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public static JsonObject parseJson(String json) {
        return parser.parse(json).getAsJsonObject();
    }
}
