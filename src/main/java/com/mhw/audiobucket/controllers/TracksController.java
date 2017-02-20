package com.mhw.audiobucket.controllers;

import com.mhw.audiobucket.app.transformer.JsonTransformer;
import com.mhw.audiobucket.config.exception.ApplicationConfigException;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.model.Track;
import com.mhw.audiobucket.persistence.TracksDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.get;

/**
 * Created by michaelwomack on 2/19/17.
 */
public class TracksController {

    private static final String CONTENT_TYPE = "application/json";
    private static final Logger LOGGER = Logger.getLogger(TracksController.class.getName());
    private static final TracksDAO tracks = new TracksDAO();

    public static void run() {

        get("/api/tracks/artist_id/:artist_id", CONTENT_TYPE, (req, res) -> {
            List<Track> tracksList;
            String artistIdStr = req.params(":artist_id");
            try {
                long artistId = Long.parseLong(artistIdStr);
                tracksList = tracks.getTracksByArtistId(artistId);
                return new Response(true, tracksList);
            } catch (ApplicationConfigException | SQLException e) {
                String message = "Error occurred while getting tracks with artist_id : " + artistIdStr + " -- " + e.getMessage();
                LOGGER.log(Level.SEVERE, message, e);
                return new Response(false, message);
            }
        }, new JsonTransformer());

        get("/api/tracks/id/:id", CONTENT_TYPE, (req, res) -> {
            String trackIdStr = req.params("id");
            try {
                long trackId = Long.parseLong(trackIdStr);
                Track track = tracks.getById(trackId);
                return new Response(true, track);
            } catch (ApplicationConfigException | SQLException e) {
                String message = "Error occurred while getting track with id: " + trackIdStr + " -- " + e.getMessage();
                LOGGER.log(Level.SEVERE, message, e);
                return new Response(false, message);
            }
        }, new JsonTransformer());
    }
}
