package com.mhw.audiobucket.controllers;

import com.google.cloud.storage.Blob;
import com.google.gson.JsonObject;
import com.mhw.audiobucket.app.transformer.JsonTransformer;
import com.mhw.audiobucket.config.StorageConfig;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Artist;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.persistence.ArtistsDAO;
import com.mhw.audiobucket.persistence.UsersDAO;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.storage.CloudStorageManager;
import com.mhw.audiobucket.util.Util;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

/**
 * Created by mxw4182 on 12/22/16.
 */
public class ArtistsController {

    private static final String CONTENT_TYPE = "application/json";
    private static final Logger LOGGER = Logger.getLogger(ArtistsController.class.getName());
    private static final ArtistsDAO artists = new ArtistsDAO();
    private static final UsersDAO users = new UsersDAO();
    private static final CloudStorageManager storageManager = new CloudStorageManager();

    public static void run() throws ApplicationConfigException {

        StorageConfig storageConfig = new StorageConfig();

        //TODO
        get("/api/artists", CONTENT_TYPE, (req, res) -> {
            Artist artist = new Artist("Michael", "Bio");
            return new ArrayList<>(Arrays.asList(artist, artist));
        }, new JsonTransformer());


        put("/api/artists", CONTENT_TYPE, (req, res) -> {
            try {
                req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
                HttpServletRequest servletRequest = req.raw();
                List<Part> parts = (ArrayList) servletRequest.getParts();

                JsonObject requestJson = new JsonObject();
                for (Part part : parts) {
                    if (!part.getName().equals("profileImage")) {
                        requestJson.addProperty(part.getName(), Util.inputStreamToString(part.getInputStream()));
                    }
                    else {
                        String userId = req.cookie("identity");
                        try (InputStream profileImageStream = part.getInputStream()) {
                            String profileImagePath = storageManager.getStorageNameForImage(userId, part.getName());
                            Blob profileImage = storageManager.uploadBlobFromInputStream(storageConfig.getProperty("bucket_name"),
                                    profileImagePath, profileImageStream, null);

                        }
                    }
                }

                Artist artist = (Artist) JsonSerializer.toObject(requestJson.toString(), Artist.class);
                if (artist.getId() == 0L) {
                    long artistId = artists.insert(artist);
                    long userId = Long.parseLong(req.cookie("identity"));
                    User user = users.getById(userId);
                    user.setArtistId(artistId);
                    users.update(user);
                    return new Response(true, "Created Artist successfully");
                } else {
                    boolean updated = artists.update(artist);
                    if (updated) {
                        return new Response(true, "Updated artist info successfully");
                    } else {
                        return new Response(false, "Failed to update artist info.");
                    }
                }
            } catch (ApplicationConfigException | SQLException | IOException e) {
                String message = "Error updating artist info";
                LOGGER.log(Level.SEVERE, message, e);
                return new Response(false, message);
            } catch (Exception e) {
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

