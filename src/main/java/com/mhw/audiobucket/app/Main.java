package com.mhw.audiobucket.app;

import com.mhw.audiobucket.app.filters.AuthorizedRequestInterceptor;
import com.mhw.audiobucket.controllers.ArtistsController;
import com.mhw.audiobucket.controllers.UsersController;
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

        before("/api/*", new AuthorizedRequestInterceptor());
        after("*", (req, res) -> { LOGGER.log(Level.INFO, req.requestMethod() + " " + req.pathInfo()); });

        UsersController.run();
        ArtistsController.run();

        /** The key here is the content type is application/json, which
         * keeps it from returning the index.html
         * **/
        get("*", CONTENT_TYPE, (req, res) -> { return Util.readResouce("public/index.html"); });

    }
}
