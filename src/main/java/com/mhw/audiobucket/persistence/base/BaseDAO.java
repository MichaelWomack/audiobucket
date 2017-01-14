package com.mhw.audiobucket.persistence.base;

import com.mhw.audiobucket.config.DatabaseConfig;

import java.io.IOException;
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

    protected abstract List<?> getAll();

    protected abstract Object getById(long id);

    protected Connection getConnection() throws SQLException, IOException {
        Connection conn = null;
        String url = getConnectionUrl();
        conn = DriverManager.getConnection(url);
        return conn;
    }

    private String getConnectionUrl() throws IOException {
        props = new DatabaseConfig();
        return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
                props.getProperty("host"),
                props.getProperty("port"),
                props.getProperty("database"),
                props.getProperty("user"),
                props.getProperty("password"));
    }
}
