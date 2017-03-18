package com.mhw.audiobucket.model.conf;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.mhw.audiobucket.config.module.DevelopmentModule;

/**
 * Created by michaelwomack on 3/15/17.
 */
public class DatabaseProperties {
    private String host;
    private String port;
    private String database;
    private String abUser;
    private String password;
    private String jdbcBase;

    @Inject
    public DatabaseProperties(@Named("host") String host,
                              @Named("port") String port,
                              @Named("database") String database,
                              @Named("user") String abUser,
                              @Named("password") String password,
                              @Named("jdbc_base") String jdbcBase) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.abUser = abUser;
        this.password = password;
        this.jdbcBase = jdbcBase;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getAbUser() {
        return abUser;
    }

    public String getPassword() {
        return password;
    }

    public String getJdbcBase() {
        return jdbcBase;
    }

    @Override
    public String toString() {
        return "DatabaseProperties{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", abUser='" + abUser + '\'' +
                ", password='" + password + '\'' +
                ", jdbcBase='" + jdbcBase + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DevelopmentModule());
        DatabaseProperties properties = injector.getInstance(DatabaseProperties.class);
        System.out.println(properties);

    }
}
