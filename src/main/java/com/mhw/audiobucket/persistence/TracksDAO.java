package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Track;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelwomack on 2/18/17.
 */
public class TracksDAO extends BaseDAO {

    private static final String GET_ALL = "select * from Tracks";
    private static final String BY_ID = " where id = ?";
    private static final String BY_ARTIST_ID = " where artist_id = ?";
    private static final String INSERT = "insert into Tracks (name, description, album_id, artist_id, type, url) " +
            "values(?,?,?,?,?,?)";
    private static final String UPDATE = "update Tracks set name=?, description=?, album_id=?, artist_id=?, type=?, url=?";

    public Track getById(long id) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL + BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            Track track = null;
            if (rs.next()){
                track = getTrackFromResultSet(rs);
            }
            return track;
        }
    }

    public List<Track> getTracksByArtistId(long artistId) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL + BY_ARTIST_ID);
            statement.setLong(1, artistId);
            ResultSet rs = statement.executeQuery();
            List<Track> tracks = new ArrayList<>();

            while (rs.next()) {
                tracks.add(getTrackFromResultSet(rs));
            }
            return tracks;
        }
    }

    public long insert(Track track) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int col = 1;
            statement.setString(col++, track.getName());
            statement.setString(col++, track.getDescription());
            statement.setLong(col++, track.getAlbumId());
            statement.setLong(col++, track.getArtistId());
            statement.setString(col++, track.getType());
            statement.setString(col++, track.getUrl());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            long id = -1;
            if (rs.next()) {
                id = rs.getLong(1);
            }
            return id;
         }
    }

    public boolean update(Track track) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE + BY_ID);
            int col = 1;
            statement.setString(col++, track.getName());
            statement.setString(col++, track.getDescription());
            statement.setLong(col++, track.getAlbumId());
            statement.setLong(col++, track.getArtistId());
            statement.setString(col++, track.getType());
            statement.setString(col++, track.getUrl());
            statement.setLong(col++, track.getId());
            return statement.executeUpdate() == 1;
        }
    }

    private Track getTrackFromResultSet(ResultSet rs) throws SQLException {
        Track track = new Track();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));
        track.setDescription(rs.getString("description"));
        track.setAlbumId(rs.getLong("album_id"));
        track.setArtistId(rs.getLong("artist_id"));
        track.setType(rs.getString("type"));
        track.setUrl(rs.getString("url"));
        return track;
    }
}
