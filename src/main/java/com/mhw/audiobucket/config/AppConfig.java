package com.mhw.audiobucket.config;

import com.mhw.audiobucket.config.base.BaseConfig;

import java.io.IOException;

/**
 * Created by michaelwomack on 1/9/17.
 */
public class AppConfig extends BaseConfig {

    public AppConfig() throws IOException {
        this.loadProperties("config/app.props");
    }
}
