package com.mhw.audiobucket.persistence.base;

import com.mhw.audiobucket.config.DatabaseConfig;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by michaelwomack on 1/14/17.
 */
public abstract class BaseDAO {

    private Properties props;

    public abstract List<?> getAll() throws ApplicationConfigException, SQLException;

    public abstract Object getById(long id) throws ApplicationConfigException, SQLException;

    protected Connection getConnection() throws SQLException, ApplicationConfigException {
        Connection conn = null;
        String url = getConnectionUrl();
        conn = DriverManager.getConnection(url);
        return conn;
    }

    private String getConnectionUrl() throws ApplicationConfigException {
        props = new DatabaseConfig();
        return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
                props.getProperty("host"),
                props.getProperty("port"),
                props.getProperty("database"),
                props.getProperty("user"),
                props.getProperty("password"));
    }
}
