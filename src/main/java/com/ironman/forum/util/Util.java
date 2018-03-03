package com.ironman.forum.util;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

public class Util {
    public static String toJson(Object src) {
        Gson gson=new Gson();
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }
}
