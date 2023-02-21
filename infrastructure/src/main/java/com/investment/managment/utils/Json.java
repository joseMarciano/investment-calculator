package com.investment.managment.utils;

import com.google.gson.Gson;

public final class Json {

    private static final Gson gson = new Gson();

    private Json() {
    }


    public static <T> T convertToObj(final String source, Class<T> target) {
        return gson.fromJson(source, target);
    }
}
