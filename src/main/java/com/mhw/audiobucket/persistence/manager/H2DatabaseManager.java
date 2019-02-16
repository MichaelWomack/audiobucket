package com.mhw.audiobucket.persistence.manager;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.mhw.audiobucket.config.module.TestModule;
import com.mhw.audiobucket.model.conf.DatabaseProperties;
import com.mhw.audiobucket.persistence.base.BaseDAO;
import org.h2.tools.Server;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by michaelwomack on 3/15/17.
 */
public class H2DatabaseManager implements DatabaseManager {

    @Inject
    private DatabaseProperties dbProps;

    @Inject
    private BaseDAO baseDAO;

    protected static Server localServer;

    @Override
    public void install() throws IOException, SQLException {

        String[] args = {""};
        if (localServer == null) {
            localServer = Server.createTcpServer().start();
        }
        baseDAO.setupDatabase();
    }
}
