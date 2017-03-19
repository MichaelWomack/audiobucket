package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class UsersDAO extends BaseDAO {

    private static final String GET_ALL_USERS = "select * from Users";
    private static final String BY_ID = " where id = ?";
    private static final String BY_EMAIL = " where email = ?";
    private static final String ADD_USER = "insert into Users (email, password, date_created, is_active) " +
            "values (?, ?, now(), FALSE )";
    private static final String UPDATE_USER = "update Users set email = ?, password = ?, artist_id = ?, is_active = ?";
    private static final String DELETE_USER = "delete from Users";

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);

            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
            }
        }
        return users;
    }

    public User getById(long id) throws SQLException {
        User user = null;
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_USERS + BY_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        }
        return user;
    }

    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_USERS + BY_EMAIL);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        }
        return user;
    }

    public long insert(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            int col = 1;
            statement.setString(col++, user.getEmail());
            statement.setString(col++, user.getPassword());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            long id = -1;
            if (rs.next()) {
                id = rs.getLong(1);
            }
            return id;
        }
    }

    public boolean update(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER + BY_ID);
            int col = 1;
            statement.setString(col++, user.getEmail());
            statement.setString(col++, user.getPassword());
            statement.setLong(col++, user.getArtistId());
            statement.setBoolean(col++, user.isActive());
            statement.setLong(col++, user.getId());
            return statement.executeUpdate() == 1;
        }
    }

    public boolean delete(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER + BY_EMAIL);
            statement.setString(1, user.getEmail());
            return statement.executeUpdate() == 1;
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setActive(resultSet.getBoolean("is_active"));
        user.setDateCreated(resultSet.getTimestamp("date_created"));
        user.setArtistId(resultSet.getLong("artist_id"));
        return user;
    }
}
