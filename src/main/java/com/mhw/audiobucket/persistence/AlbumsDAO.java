package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Album;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.sql.*;

/**
 * Created by michaelwomack on 2/18/17.
 */
public class AlbumsDAO extends BaseDAO {

    private static final String INSERT = "insert into Albums (name, description, release_date, artist_id) " +
            "values(?, ?, ?, ?)";
    private static final String UPDATE = "update Albums set name=?, description=?, release_date=?, artist_id=?";
    private static final String GET_ALL = "select * from Albums";
    private static final String BY_ID = " where id = ?";


    public Album getById(long id) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL + BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            Album album = getAlbumFromResultSet(rs);
            return album;
        }
    }

    public long insert(Album album) throws SQLException {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int col = 1;
            statement.setString(col++, album.getName());
            statement.setString(col++, album.getDescription());
            statement.setDate(col++, new java.sql.Date(album.getReleaseDate().getTime()));
            statement.setLong(col++, album.getArtistId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            long id = -1;
            while (rs.next()) {
                id = rs.getLong(1);
            }
            return id;
        }
    }

    public boolean update(Album album) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE + BY_ID);
            int col = 1;
            statement.setString(col++, album.getName());
            statement.setString(col++, album.getDescription());
            statement.setDate(col++, new java.sql.Date(album.getReleaseDate().getTime()));
            statement.setLong(col++, album.getArtistId());
            statement.setLong(col++, album.getId());
            return statement.executeUpdate() == 1;
        }
    }

    private Album getAlbumFromResultSet(ResultSet rs) throws SQLException {
        Album album = new Album();
        album.setId(rs.getLong("id"));
        album.setName(rs.getString("name"));
        album.setArtistId(rs.getLong("artist_id"));
        album.setDescription(rs.getString("description"));
        album.setReleaseDate(rs.getDate("release_date"));
        return album;
    }
}
