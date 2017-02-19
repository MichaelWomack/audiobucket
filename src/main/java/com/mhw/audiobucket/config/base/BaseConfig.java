package com.mhw.audiobucket.config.base;

import com.mhw.audiobucket.app.lcp.LifeCycle;
import com.mhw.audiobucket.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by michaelwomack on 1/14/17.
 */
public abstract class BaseConfig extends Properties {

    protected String getPropertiesPath() {
        return "config/" + LifeCycle.valueOf(System.getenv("AB_LCP")).toString().toLowerCase() + "/";
    }

    protected void loadProperties(String resource) throws IOException {
        try (InputStream in = Util.getResourceAsStream(resource)){
            this.load(in);
        }
    }
}
