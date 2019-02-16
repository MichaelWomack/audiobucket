package com.mhw.audiobucket.persistence.base;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.mhw.audiobucket.config.module.TestModule;
import com.mhw.audiobucket.model.conf.DatabaseProperties;
import com.mhw.audiobucket.persistence.UsersDAO;
import com.mhw.audiobucket.util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class BaseDAO {

    @Inject
    private DatabaseProperties props;

    protected Connection getConnection() throws SQLException {
        String connectionStr = getConnectionString();
        Connection conn = DriverManager.getConnection(connectionStr,
                props.getAbUser(), props.getPassword());
        return conn;
    }

    public void setupDatabase() throws IOException, SQLException {
        // TODO: 3/15/17 change sql to create table if not exisit
        String createTablesSql = Util.readResource("sql/table_definitions.sql");
        getConnection().createStatement().execute(createTablesSql);
    }

    public ResultSet showTables() throws SQLException {
        ResultSet rs = getConnection().createStatement().executeQuery("show tables");
        return rs;
    }

    private String getConnectionString() {
        return String.format("%s%s:%s/%s",
                props.getJdbcBase(),
                props.getHost(),
                props.getPort(),
                props.getDatabase());
    }

    public static void main(String[] args) throws SQLException {
        UsersDAO user = Guice.createInjector(new TestModule()).getInstance(UsersDAO.class);
        user.getConnection();
    }
}
