package com.mhw.audiobucket.controllers;

import com.google.common.base.Strings;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Artist;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.persistence.ArtistsDAO;
import com.mhw.audiobucket.persistence.UsersDAO;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.app.transformer.JsonTransformer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Created by mxw4182 on 12/22/16.
 */
public class ArtistsController {

    private static final String CONTENT_TYPE = "application/json";
    private static final Logger LOGGER = Logger.getLogger(ArtistsController.class.getName());
    private static final ArtistsDAO artists = new ArtistsDAO();
    private static final UsersDAO users = new UsersDAO();

    public static void run() {

        //TODO
        get("/api/artists", CONTENT_TYPE, (req, res) -> {
            Artist artist = new Artist("Michael", "Bio");
            return new ArrayList<>(Arrays.asList(artist, artist));
        }, new JsonTransformer());


        put("/api/artists", CONTENT_TYPE, (req, res) -> {
            LOGGER.log(Level.INFO, req.body());
            try {
                Artist artist = (Artist) JsonSerializer.toObject(req.body(), Artist.class);
                if (artist.getId() == 0L) {
                    long artistId = artists.insert(artist);
                    long userId = Long.parseLong(req.cookie("identity"));
                    User user = users.getById(userId);
                    user.setArtistId(artistId);
                    users.update(user);
                    return new Response(true, "Created Artist successfully");
                }
                else {
                    boolean updated = artists.update(artist);
                    if (updated) {
                        return new Response(true, "Updated artist info successfully");
                    }
                    else {
                        return new Response(false, "Failed to update artist info.");
                    }
                }
            } catch (ApplicationConfigException | SQLException e) {
                String message = "Error updating artist info";
                LOGGER.log(Level.SEVERE, message, e);
                return new Response(false, message);
            }
        }, new JsonTransformer());


        post("/api/artists", CONTENT_TYPE, (req, res) -> {
            try {
                Artist artist = (Artist) JsonSerializer.toObject(req.body(), Artist.class);
                long artistId = artists.insert(artist);
                long userId = Long.parseLong(req.cookie("identity"));
                User user = users.getById(userId);
                user.setArtistId(artistId);
                users.update(user);
                return new Response(true, "Successfully inserted artist. Id: " + artistId);
            } catch (ApplicationConfigException | SQLException e) {
                String message = "Error submitting artist info: " + e.getMessage();
                LOGGER.log(Level.SEVERE, message, e);
                return new Response(false, message);
            }
        }, new JsonTransformer());


        get("/api/artists/id/:id", CONTENT_TYPE, (req, res) -> {
            String idStr = req.params(":id");
            try {
                long artistId = Long.parseLong(idStr);
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

