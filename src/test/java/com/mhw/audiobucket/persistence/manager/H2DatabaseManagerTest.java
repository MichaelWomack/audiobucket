package com.mhw.audiobucket.persistence.manager;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.mhw.audiobucket.config.module.TestModule;
import com.mhw.audiobucket.persistence.base.BaseDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by michaelwomack on 3/18/17.
 */
class H2DatabaseManagerTest {

    @Inject
    private H2DatabaseManager manager;

    @Inject
    private BaseDAO baseDAO;

    @BeforeEach
    void setUp() throws IOException, SQLException {
        Guice.createInjector(new TestModule()).injectMembers(this);
    }

    @Test
    public void testH2ManagerInstallsCorrectly() throws SQLException, IOException {
        manager.install();
        assertNotNull(H2DatabaseManager.localServer);
        ResultSet rs = baseDAO.showTables();
        List<String> tableList = new ArrayList<>();

        while (rs.next()) {
            tableList.add(rs.getString(1));
        }

        assertTrue(tableList.contains("ALBUMS"));
        assertTrue(tableList.contains("ARTISTS"));
        assertTrue(tableList.contains("TRACKS"));
        assertTrue(tableList.contains("USERS"));
    }
}