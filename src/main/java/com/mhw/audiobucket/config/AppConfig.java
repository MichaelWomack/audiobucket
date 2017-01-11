package com.mhw.audiobucket.config;

import com.mhw.audiobucket.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by michaelwomack on 1/9/17.
 */
public class AppConfig extends Properties {

    private final String resource = "config/app.props";

    private void loadProperties() throws IOException {
        try (InputStream in = Util.getResourceAsStream(resource)){
            this.load(Util.getResourceAsStream(resource));
        }
    }
}
