package com.mhw.audiobucket.config;

import com.mhw.audiobucket.config.base.BaseConfig;
import com.mhw.audiobucket.exceptions.ApplicationConfigException;

import java.io.IOException;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class DatabaseConfig extends BaseConfig {

    public DatabaseConfig() throws ApplicationConfigException {
        try {
            this.loadProperties("config/db.props");
        } catch (IOException e) {
            throw new ApplicationConfigException("Error occurred while loading 'db.props'", e);
        }
    }
}
