package com.mhw.audiobucket.config.module.base;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.mhw.audiobucket.util.Util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by michaelwomack on 3/15/17.
 */
public class PropertyModule extends AbstractModule {

    private String propertyFilePath;
    private final Logger LOGGER = Logger.getLogger(PropertyModule.class.getName());

    public PropertyModule(String propertyFilePath) {
        this.propertyFilePath = propertyFilePath;
    }

    @Override
    protected void configure() {
        Properties props = new Properties();
        try {
            props.load(Util.getResourceAsStream(this.propertyFilePath));
            Names.bindProperties(binder(), props);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not load properties for " + this.propertyFilePath, e);
            System.exit(1);
        }
    }
}
