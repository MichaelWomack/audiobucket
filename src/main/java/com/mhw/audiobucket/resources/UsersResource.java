package com.mhw.audiobucket.resources;

import com.google.gson.JsonObject;
import com.mhw.audiobucket.security.JwtUtil;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.serialization.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by michaelwomack on 1/8/17.
 */
public class UsersResource {


    public static void run() {

        get("/api/users", (req, res) -> {
            return "users all users";
        }, new JsonTransformer());


        get("/api/users/id/:id", (req, res) -> {
            long id;
            try {
                id = Long.parseLong(req.params(":id"));
            } catch (Exception e) {
                return new Response(false, "Invalid id");
            }
            return new Response(true, "Getting user with id " + id);
        }, new JsonTransformer());


        get("/api/users/identity", (req, res) -> {
            return new Response(true, "Hello");
        }, new JsonTransformer());


        /**
         * Routes below do not require auth
         */
        post("/users/login", (req, res) -> {
            JsonObject json = JsonSerializer.parseJson(req.body());
            String email = json.get("email").getAsString();
            String password = json.get("password").getAsString();
            long id = 1;
            String token = JwtUtil.createJWT(1);
            res.header("access-token", token);
            return new Response(true, "Successful Login.");
        }, new JsonTransformer());

        post("/users/register", (req, res) -> {
            return "registering new user";
        }, new JsonTransformer());
    }
}
