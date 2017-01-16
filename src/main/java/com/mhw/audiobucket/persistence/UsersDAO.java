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

    @Override
    protected List<User> getAll() throws ApplicationConfigException, SQLException {
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
    protected User getById(long id) throws ApplicationConfigException, SQLException {
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

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getLong("id"));
        user.setActive(resultSet.getBoolean("is_active"));
        user.setDateCreated(resultSet.getTimestamp("date_created"));
        user.setArtistId(resultSet.getLong("artist_id"));
        return user;
    }

    public static void main(String[] args) throws ApplicationConfigException, SQLException {
        UsersDAO users = new UsersDAO();
        User user = users.getById(1);
        System.out.println(user);
    }
}
