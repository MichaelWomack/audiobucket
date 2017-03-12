package com.mhw.audiobucket.persistence;

import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        users.delete(testUser);
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

    @Test
    void testGetUserById() throws SQLException, ApplicationConfigException {
        long id = users.insert(testUser);
        testUser.setId(id);
        User resultUser = users.getById(id);
        assertEquals(testUser.getId(), resultUser.getId());
        assertEquals(testUser.getEmail(), resultUser.getEmail());
    }

    @Test
    void testDeleteUser() throws SQLException, ApplicationConfigException {
        long id = users.insert(testUser);
        testUser.setId(id);
        assertTrue(users.delete(testUser));
        assertNull(users.getUserByEmail(testUser.getEmail()));
    }

    @Test
    void testGetAllUsers() throws SQLException, ApplicationConfigException {
        long id = users.insert(testUser);
        List<User> userList = users.getAll();
        assertNotNull(userList);

        boolean containsTestUser = false;
        for (User user : userList) {
            if (user.getEmail().equals(testUser.getEmail()) && user.getId() == id) {
                containsTestUser = true;
            }
        }
        assertTrue(containsTestUser);
    }
}