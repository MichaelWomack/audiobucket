package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.persistence.base.BaseDAO;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by michaelwomack on 1/14/17.
 */
public class UsersDAO extends BaseDAO {

    private static final String GET_ALL_USERS = "select * from users";
    private static final String BY_ID = " where id = ?";
    private static final String BY_EMAIL = " where email = ?";
    private static final String ADD_USER = "insert into Users (email, password, date_created, is_active) " +
            "values (?, ?, now(), FALSE )";
    private static final String UPDATE_USER = "update Users set email = ?, password = ?, artist_id = ?, is_active = ?";

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

    public long insert(User user) throws SQLException, ApplicationConfigException {
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

    public boolean update(User user) throws SQLException, ApplicationConfigException {
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
        User user = new User();
        user.setDateCreated(new Date());
        user.setActive(false);
        user.setPassword("temporaryPwd");
        user.setEmail("mike@gmail.com");

        System.out.println(users.insert(user));

    }
}
