package com.waymaps.util;

import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONUtil {

    private static final ObjectMapper ourInstance = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return ourInstance;
    }


    public static Bundle setValueToBundle(Bundle bundle, String key, Object value) throws JsonProcessingException {
        bundle.putString(key, getObjectMapper().writeValueAsString(value));
        return bundle;
    }

    public static <T> T getObjectFromBundle(Bundle bundle, String key, Class<T> tClass) throws IOException {
        if (bundle.getString(key)!=null)
            return getObjectMapper().readValue(bundle.getString(key), tClass);
        else return null;
    }

}
