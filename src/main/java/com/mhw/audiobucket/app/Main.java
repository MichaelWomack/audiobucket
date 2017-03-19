package com.mhw.audiobucket.app;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mhw.audiobucket.app.filters.AuthorizedRequestInterceptor;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.config.module.DevelopmentModule;
import com.mhw.audiobucket.model.Track;
import com.mhw.audiobucket.services.ArtistsService;
import com.mhw.audiobucket.services.TracksService;
import com.mhw.audiobucket.services.UsersService;
import com.mhw.audiobucket.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
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


    public static void main(String[] args) throws UnknownHostException, ApplicationConfigException {

        Injector injector = Guice.createInjector(new DevelopmentModule());
        UsersService usersService = injector.getInstance(UsersService.class);
        ArtistsService artistsService = injector.getInstance(ArtistsService.class);
        TracksService tracksService = injector.getInstance(TracksService.class);


        port(8080);
        boolean localhost = isLocalHost();
        if (localhost) {
            staticFiles.externalLocation(STATIC_RESOURCES);
        } else {
            staticFiles.location("/public");
        }

        before("/api/*", new AuthorizedRequestInterceptor());
        after("*", (req, res) -> {
            LOGGER.log(Level.INFO, req.requestMethod() + " " + req.pathInfo());
        });

        usersService.init();
        artistsService.init();
        tracksService.init();

        /** The key here is the content type is application/json, which
         * keeps it from returning the index.html
         * **/
        get("*", CONTENT_TYPE, (req, res) -> {
            return Util.readResource("public/index.html");
        });
    }

    private static boolean isLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName().contains("MacBook-Pro.local");
    }
}
