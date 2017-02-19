package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Album;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by michaelwomack on 2/18/17.
 */
public class AlbumsDAO extends BaseDAO {


    @Override
    public List<?> getAll() throws ApplicationConfigException, SQLException {
        return null;
    }

    @Override
    public Album getById(long id) throws ApplicationConfigException, SQLException {
        try (Connection conn = getConnection()) {

        }
    }
}
