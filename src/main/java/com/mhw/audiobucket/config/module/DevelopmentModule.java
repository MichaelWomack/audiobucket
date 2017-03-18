package com.mhw.audiobucket.config.module;

import com.google.inject.AbstractModule;

/**
 * Created by michaelwomack on 3/15/17.
 */
public class DevelopmentModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PropertyModule("config/dev/app.props"));
        install(new PropertyModule("config/dev/db.props"));
        install(new PropertyModule("config/dev/storage.props"));
    }
}
