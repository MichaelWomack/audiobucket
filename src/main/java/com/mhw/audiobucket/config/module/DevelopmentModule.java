package com.mhw.audiobucket.config.module;

import com.google.inject.AbstractModule;
import com.mhw.audiobucket.config.module.base.PropertyModule;
import com.mhw.audiobucket.persistence.manager.CloudDatabaseManager;
import com.mhw.audiobucket.persistence.manager.DatabaseManager;

/**
 * Created by michaelwomack on 3/15/17.
 */
public class DevelopmentModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PropertyModule("config/dev/app.props"));
        install(new PropertyModule("config/dev/db.props"));
        install(new PropertyModule("config/dev/storage.props"));

        bind(DatabaseManager.class).to(CloudDatabaseManager.class);
    }
}
