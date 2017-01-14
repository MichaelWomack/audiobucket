package com.mhw.audiobucket.config;

import com.mhw.audiobucket.config.base.BaseConfig;

import java.io.IOException;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class DatabaseConfig extends BaseConfig {

    public DatabaseConfig() throws IOException {
        this.loadProperties("config/db.props");
    }
}
