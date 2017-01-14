package com.mhw.audiobucket.config;

import com.mhw.audiobucket.config.base.BaseConfig;

import java.io.IOException;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class DatabaseConfig extends BaseConfig {

    public DatabaseConfig() throws IOException {
        this.loadProperties("config/db.props");
    }

    public static void main(String[] args) throws IOException {
        DatabaseConfig db = new DatabaseConfig();
        for (String prop: db.stringPropertyNames()) {
            System.out.println(prop + " " + db.getProperty(prop));
        }
    }
}
