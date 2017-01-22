package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.exceptions.ApplicationConfigException;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class UsersDAO extends BaseDAO {

    private static final String GET_ALL_USERS = "select * from users";
    private static final String BY_ID = " where id = ?";
    private static final String BY_EMAIL = " where email = ?";
    private static final String ADD_USER = "insert into Users (email, password, date_created, is_active) " +
            "values (?, ?, now(), FALSE )";
    private static final String UPDATE_USER = "update table Users set email = ?, password = ?";

    @Override
    public List<User> getAll() throws ApplicationConfigException, SQLException {
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

    @Override
    public User getById(long id) throws ApplicationConfigException, SQLException {
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

    public User getUserByEmail(String email) throws SQLException, ApplicationConfigException {
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

    public long addUser(User user) throws SQLException, ApplicationConfigException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            int col = 1;
            statement.setString(col++, user.getEmail());
            statement.setString(col++, user.getPassword());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            long id = -1;
            if (rs.next()) {
                id = rs.getLong("id");
            }
            return id;
        }
    }

    public boolean updateUser(User user) throws SQLException, ApplicationConfigException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER + BY_ID);
            int col = 1;
            statement.setString(col++, user.getEmail());
            statement.setString(col++, user.getPassword());
            return statement.execute();
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

    public static void main(String[] args) throws ApplicationConfigException, SQLException {
        UsersDAO users = new UsersDAO();
    }
}
