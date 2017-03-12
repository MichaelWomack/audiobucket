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

    private DatabaseConfig props;

    protected Connection getConnection() throws SQLException, ApplicationConfigException {
        props = new DatabaseConfig();
        String url = getConnectionUrl();
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    private String getConnectionUrl() throws ApplicationConfigException {
        return String.format("jdbc:mysql://%s:%s/%s",
                props.getProperty("host"),
                props.getProperty("port"),
                props.getProperty("database"));
    }
}
