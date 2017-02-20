package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Artist;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.sql.*;
import java.util.List;

/**
 * Created by michaelwomack on 2/18/17.
 */
public class ArtistsDAO extends BaseDAO {

    private static final String GET_ALL = "select * from Artists";
    private static final String INSERT = "insert into Artists(name, bio) values(?,?)";
    private static final String UPDATE = "update Artists set name=?, bio=?";
    private static final String BY_ID = " where id = ?";

    @Override
    public List<?> getAll() throws ApplicationConfigException, SQLException {
        return null;
    }

    @Override
    public Artist getById(long id) throws ApplicationConfigException, SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL + BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            Artist artist = null;
            if (rs.next()) {
                artist = getArtistFromResultSet(rs);
            }
            return artist;
        }
    }

    //    public Artist getByUserId(long id) throws SQLException, ApplicationConfigException {
//        try (Connection conn = getConnection()) {
//            PreparedStatement statement = conn.prepareStatement(GET_ALL + BY_USER_ID);
//        }
//    }
    public long insert(Artist artist) throws SQLException, ApplicationConfigException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int col = 1;
            statement.setString(col++, artist.getName());
            statement.setString(col++, artist.getBio());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();

            long id = -1;
            while (rs.next()) {
                id = rs.getLong(1);
            }
            return id;
        }
    }

    public boolean update(Artist artist) throws SQLException, ApplicationConfigException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE + BY_ID);
            int col = 1;
            statement.setString(col++, artist.getName());
            statement.setString(col++, artist.getBio());
            statement.setLong(col++, artist.getId());
            return statement.executeUpdate() == 1;
        }
    }

    private Artist getArtistFromResultSet(ResultSet rs) throws SQLException {
        Artist artist = new Artist();
        artist.setId(rs.getLong("id"));
        artist.setBio(rs.getString("bio"));
        artist.setName(rs.getString("name"));
        return artist;
    }

    public static void main(String[] args) throws SQLException, ApplicationConfigException {
        ArtistsDAO dao = new ArtistsDAO();
        System.out.println(dao.getById(1));
    }
}
