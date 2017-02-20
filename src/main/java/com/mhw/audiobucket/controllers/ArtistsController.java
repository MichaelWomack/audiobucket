package com.mhw.audiobucket.controllers;

import com.google.common.base.Strings;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Artist;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.persistence.ArtistsDAO;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.app.transformer.JsonTransformer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by mxw4182 on 12/22/16.
 */
public class ArtistsController {

    private static final String CONTENT_TYPE = "application/json";
    private static final Logger LOGGER = Logger.getLogger(ArtistsController.class.getName());
    private static final ArtistsDAO artists = new ArtistsDAO();

    public static void run() {

        get("/api/artists", CONTENT_TYPE, (req, res) -> {
            Artist artist = new Artist("Michael", "Bio");
            return new ArrayList<>(Arrays.asList(artist, artist));
        }, new JsonTransformer());


        post("/api/artists", CONTENT_TYPE, (req, res) -> {
            Artist artist = (Artist) JsonSerializer.toObject(req.body(), Artist.class);
            Response response = new Response(true, artist);
            return response;
        }, new JsonTransformer());


        get("/api/artists/id/:id", CONTENT_TYPE, (req, res) -> {
            String idStr = req.params(":id");
            try {
                long artistId = Long.parseLong(idStr);
                LOGGER.log(Level.INFO, String.valueOf(artistId));
                Artist artist = artists.getById(artistId);
                LOGGER.log(Level.INFO, artist.toString());
                return new Response(true, artist);
            } catch (ApplicationConfigException | SQLException e) {
                String message = "Error occurred while getting artist with id: " + idStr + " -- " + e.getMessage();
                LOGGER.log(Level.SEVERE, message, e);
                return new Response(false, message);
            }
        }, new JsonTransformer());


        get("/api/artists/token", CONTENT_TYPE, (req, res) -> {
            String authHeader = req.headers("Authorization");
            String token = authHeader.split("Bearer ")[1];

            //JWT jwt = JwtUtil.verify(token);
            return new Response(true, token);
        }, new JsonTransformer());

    }
}

