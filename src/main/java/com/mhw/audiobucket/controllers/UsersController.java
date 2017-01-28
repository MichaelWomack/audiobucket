package com.mhw.audiobucket.controllers;

import com.google.gson.JsonObject;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.security.exception.JwtException;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.persistence.UsersDAO;
import com.mhw.audiobucket.security.JwtUtil;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.app.transformer.JsonTransformer;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Created by michaelwomack on 1/8/17.
 */
public class UsersController {

    private static final Logger LOGGER = Logger.getLogger(UsersController.class.getName());
    private static final String CONTENT_TYPE = "application/json";
    private static UsersDAO users = new UsersDAO();

    public static void run() {

        get("/api/users", CONTENT_TYPE, (req, res) -> {
            try {
                List<User> allUsers = users.getAll();
                return new Response(true, allUsers);
            } catch (SQLException | ApplicationConfigException e) {
                return new Response(false, "Error occurred while getting users: " + e);
            }
        }, new JsonTransformer());


        get("/api/users/id/:id", CONTENT_TYPE, (req, res) -> {
            long id;
            try {
                id = Long.parseLong(req.params(":id"));
            } catch (Exception e) {
                return new Response(false, "Invalid id");
            }
            return new Response(true, "Getting user with id " + id);
        }, new JsonTransformer());

        put("/api/users/id/:id", CONTENT_TYPE, (req, res) -> {
            User user = (User) JsonSerializer.toObject(req.body(), User.class);
            boolean success = users.updateUser(user);
            String message = success ? "Updated information successfully." : "Failed to update information.";
            return new Response(success, message);
        });

        get("/api/users/identity", CONTENT_TYPE, (req, res) -> {
            String identity = req.cookie("identity");
            User user;
            if (identity != null) {
                user = users.getById(Long.parseLong(identity));
                LOGGER.log(Level.INFO, user.toString());
                return new Response(true, user);
            }
            return new Response(false, "Unable to fetch user data.");
        }, new JsonTransformer());


        /**
         * Routes below do not require auth
         */
        post("/users/login", CONTENT_TYPE, (req, res) -> {
            try {
                JsonObject json = JsonSerializer.parseJson(req.body());
                String email = json.get("email").getAsString();
                String password = json.get("password").getAsString();
                User user = users.getUserByEmail(email);
                if (user == null) {
                    return new Response(false, "Incorrect email.");
                }
                boolean correctPwd = BCrypt.checkpw(password, user.getPassword());
                if (correctPwd) {
                    try {
                        String token = JwtUtil.createJWT(user.getId());
                        System.out.println(token);
                        return new Response(true, "Successful Login.", token);
                    } catch (JwtException e) {
                        String message = "Failed to create token: " + e.getMessage();
                        LOGGER.log(Level.SEVERE, message, e);
                        return new Response(false, message);
                    }
                } else {
                    return new Response(false, "Incorrect credentials.");
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Errors", e);
                return new Response(false, e.getMessage());
            }

        }, new JsonTransformer());

        post("/users/register", CONTENT_TYPE, (req, res) -> {
            JsonObject body = JsonSerializer.parseJson(req.body());
            String email = body.get("email").getAsString();
            String password = body.get("password").getAsString();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(email, hashedPassword);
            String message = email + " successfully registered!";
            long id = -1;
            try {
                id = users.addUser(user);
            } catch (Exception e) {
                message = "Failed to register user " + email;
                LOGGER.log(Level.SEVERE, message, e);
            }
            return new Response(true, message);
        }, new JsonTransformer());
    }


}
