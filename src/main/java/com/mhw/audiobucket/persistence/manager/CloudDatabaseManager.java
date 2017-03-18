package com.mhw.audiobucket.persistence.manager;

import com.google.inject.Inject;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by michaelwomack on 3/15/17.
 */
public class CloudDatabaseManager implements DatabaseManager {

    @Inject
    private BaseDAO baseDAO;

    @Override
    public void install() throws IOException, SQLException {
        baseDAO.setupDatabase();
    }
}
