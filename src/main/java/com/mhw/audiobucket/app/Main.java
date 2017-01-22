package com.mhw.audiobucket.app;

import com.auth0.jwt.JWT;
import com.mhw.audiobucket.controllers.ArtistsController;
import com.mhw.audiobucket.controllers.UsersController;
import com.mhw.audiobucket.security.JwtUtil;
import com.mhw.audiobucket.util.Util;

import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

/**
 * Created by mxw4182 on 12/14/16.
 */
public class Main {

    public static final String CONTENT_TYPE = "application/json";
    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static final String STATIC_RESOURCES = "src/main/resources/public";

    public static void main(String[] args) {

        port(8080);
        staticFiles.externalLocation(STATIC_RESOURCES);

        before("/api/*", (req, res) -> {
            String authHeader = req.headers("Authorization");
            if (authHeader != null) {
                try {
                    String jwtStr = authHeader.split("Bearer ")[1];
                    JWT jwt = JwtUtil.verify(jwtStr);
                    res.cookie("identity", jwt.getSubject());
                    LOGGER.log(Level.INFO, jwtStr);
                } catch (Exception e) {
                    LOGGER.log(Level.INFO, req.headers().toString());
                    req.headers().remove("Authorization");
                    LOGGER.log(Level.SEVERE, "Error decoding token from Authorization header: " + authHeader, e);
                    halt(401, "No authentication token.");
                }
            }
            else {
                String message = "No Authorization header to check.";
                halt(401, message);
                LOGGER.log(Level.INFO, message);
            }
        });

        UsersController.run();
        ArtistsController.run();

        get("*" , (req, res) -> {
           return Util.readResouce("public/index.html");
        });

        after("*", (req, res) -> {
            LOGGER.log(Level.INFO, req.requestMethod() + " " + req.pathInfo());
        });

    }
}
