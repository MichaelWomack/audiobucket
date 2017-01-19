package com.mhw.audiobucket.controllers;

import com.auth0.jwt.JWT;
import com.google.gson.JsonObject;
import com.mhw.audiobucket.security.EncryptionUtil;
import com.mhw.audiobucket.security.JwtUtil;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.serialization.JsonTransformer;
import org.mindrot.jbcrypt.BCrypt;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by michaelwomack on 1/8/17.
 */
public class UsersController {

    private static final String CONTENT_TYPE = "application/json";

    public static void run() {

        get("/api/users", CONTENT_TYPE, (req, res) -> {
            return "users all users";
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


        get("/api/users/identity", CONTENT_TYPE, (req, res) -> {
            return new Response(true, "Hello");
        }, new JsonTransformer());


        /**
         * Routes below do not require auth
         */
        post("/users/login", CONTENT_TYPE, (req, res) -> {
            JsonObject json = JsonSerializer.parseJson(req.body());
            String email = json.get("email").getAsString();
            String password = json.get("password").getAsString();
            /** Get user info from DB, check password matches salt and get set id in token **/
            String token = JwtUtil.createJWT(1);
            return new Response(true, "Successful Login.", token);
        }, new JsonTransformer());

        post("/users/register", CONTENT_TYPE, (req, res) -> {
            JsonObject body = JsonSerializer.parseJson(req.body());
            String email = body.get("email").getAsString();
            String password = body.get("password").getAsString();

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            return new Response(true, body.getAsString());
        }, new JsonTransformer());
    }
}
