package com.mhw.audiobucket.config;

import com.mhw.audiobucket.config.base.BaseConfig;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;

import java.io.IOException;

/**
 * Created by michaelwomack on 1/9/17.
 */
public class AppConfig extends BaseConfig {

    public AppConfig() throws ApplicationConfigException {
        try {

            this.loadProperties(this.getPropertiesPath() + "app.props");
        } catch (IOException e) {
            throw new ApplicationConfigException("Error occurred while loading 'app.props'", e);
        }
    }
}
