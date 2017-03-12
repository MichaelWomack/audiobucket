package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by michaelwomack on 3/12/17.
 */
class UsersDAOTest {

    private static UsersDAO users;
    private static User testUser;

    @BeforeAll
    static void setUp() {
        users = new UsersDAO();
        testUser =  new User("test@test.com", "test");
    }

    @AfterEach
    void tearDown() throws SQLException, ApplicationConfigException {
        assertTrue(users.delete(testUser));
    }

    @Test
    void testInsertUser() throws SQLException, ApplicationConfigException {
        long id = users.insert(testUser);
        User resultUser = users.getUserByEmail(testUser.getEmail());
        assertEquals(id, resultUser.getId());
    }

    @Test
    void testUpdateUser() throws SQLException, ApplicationConfigException {
        long id = users.insert(testUser);
        testUser.setId(id);
        testUser.setPassword("new_password");
        assertTrue(users.update(testUser));

        User resultUser = users.getUserByEmail(testUser.getEmail());
        assertEquals("new_password", resultUser.getPassword());
        assertEquals(id, resultUser.getId());
    }
}