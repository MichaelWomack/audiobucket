package com.mhw.audiobucket.app;

import com.mhw.audiobucket.controllers.ArtistsController;
import com.mhw.audiobucket.controllers.UsersController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

/**
 * Created by mxw4182 on 12/14/16.
 */
public class Main {

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static final String STATIC_RESOURCES = "src/main/resources/public";

    public static void main(String[] args) {
        port(8080);
        staticFiles.externalLocation(STATIC_RESOURCES);

        UsersController.run();
        ArtistsController.run();

        after("*", (req, res) -> {
            LOGGER.log(Level.INFO, req.requestMethod() + " " + req.pathInfo());
        });

        notFound((req, res) -> {
            res.redirect("/");
            return req;
        });
    }
}
