package com.mhw.audiobucket.persistence.base;

import com.mhw.audiobucket.config.DatabaseConfig;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by michaelwomack on 1/14/17.
 */
public abstract class BaseDAO {

    private DatabaseConfig config;

    protected Connection getConnection() throws SQLException, ApplicationConfigException {
        config = new DatabaseConfig();
        String url = getConnectionUrl();
        String user = config.getProperty("user");
        String password = config.getProperty("password");
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    private String getConnectionUrl() throws ApplicationConfigException {
        return String.format("%s%s:%s/%s",
                config.getProperty("jdbc_base"),
                config.getProperty("host"),
                config.getProperty("port"),
                config.getProperty("database"));
    }
}
