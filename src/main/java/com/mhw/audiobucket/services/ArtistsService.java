package com.mhw.audiobucket.services;

import com.google.cloud.storage.Blob;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.mhw.audiobucket.app.transformer.JsonTransformer;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Artist;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.model.User;
import com.mhw.audiobucket.model.conf.StorageProperties;
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
public class ArtistsService {

    private static final String CONTENT_TYPE = "application/json";
    private static final Logger LOGGER = Logger.getLogger(ArtistsService.class.getName());

    @Inject
    private ArtistsDAO artists;

    @Inject
    private UsersDAO users;

    @Inject
    private CloudStorageManager storageManager;

    @Inject
    private StorageProperties storageProperties;

    public void init() throws ApplicationConfigException {


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
                List<String> imageInfo = new ArrayList<>(Arrays.asList("profileImage", "contentType"));

                for (Part part : parts) {
                    if (!imageInfo.contains(part.getName())) {
                        requestJson.addProperty(part.getName(), Util.inputStreamToString(part.getInputStream()));
                    }
                }

                Part profileImagePart = servletRequest.getPart("profileImage");
                String contentType = Util.inputStreamToString(servletRequest.getPart("fileType").getInputStream());

                String relativeImageUrl;
                try (InputStream profileImageStream = profileImagePart.getInputStream()) {
                    String userId = req.cookie("identity");
                    relativeImageUrl = storageProperties.getStorageNameForImage(userId, profileImagePart.getName());
                    Blob profileImageBlob = storageManager.uploadBlobFromInputStream(storageProperties.getBucketName(),
                            relativeImageUrl, profileImageStream, contentType);
                }

                Artist artist = (Artist) JsonSerializer.toObject(requestJson.toString(), Artist.class);
                artist.setProfileImageUrl(storageProperties.getFullStorageObjectUrl(relativeImageUrl));
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
            } catch (SQLException | IOException e) {
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
            } catch (SQLException e) {
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
            } catch (SQLException e) {
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

