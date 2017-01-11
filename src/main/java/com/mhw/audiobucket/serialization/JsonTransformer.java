package com.mhw.audiobucket.serialization;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by mxw4182 on 12/16/16.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}

