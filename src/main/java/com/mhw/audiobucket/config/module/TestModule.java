package com.mhw.audiobucket.config.module;

import com.google.inject.AbstractModule;
import com.mhw.audiobucket.config.module.base.PropertyModule;
import com.mhw.audiobucket.persistence.manager.DatabaseManager;
import com.mhw.audiobucket.persistence.manager.H2DatabaseManager;

/**
 * Created by michaelwomack on 3/18/17.
 */
public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PropertyModule("config/test/app.props"));
        install(new PropertyModule("config/test/db.props"));
        install(new PropertyModule("config/test/storage.props"));

        bind(DatabaseManager.class).to(H2DatabaseManager.class);
    }
}
