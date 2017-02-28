package com.mhw.audiobucket.config;

import com.mhw.audiobucket.config.base.BaseConfig;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;

import java.io.IOException;

/**
 * Created by michaelwomack on 2/27/17.
 */
public class StorageConfig extends BaseConfig {

    public StorageConfig() throws ApplicationConfigException {
        try {
            this.loadProperties(this.getPropertiesPath() + "storage.props");

        } catch (IOException e) {
            throw new ApplicationConfigException("Error occurred while loading 'storage.props'");
        }
    }
}
