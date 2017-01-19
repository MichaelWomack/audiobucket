package com.mhw.audiobucket.controllers;

import com.auth0.jwt.JWT;
import com.google.common.base.Strings;
import com.mhw.audiobucket.security.JwtUtil;
import com.mhw.audiobucket.model.Artist;
import com.mhw.audiobucket.model.Response;
import com.mhw.audiobucket.serialization.JsonSerializer;
import com.mhw.audiobucket.serialization.JsonTransformer;

import java.util.ArrayList;
import java.util.Arrays;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by mxw4182 on 12/22/16.
 */
public class ArtistsController {

    private static final String CONTENT_TYPE = "application/json";

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

            if (Strings.isNullOrEmpty(idStr)) {
                return new Response(false, "Invalid param 'id'");
            }

            long id = Long.parseLong(req.params(":id"));
            Artist artist = new Artist("Michael", String.valueOf(id));
            return new Response(true, artist);
        }, new JsonTransformer());


        get("/api/artists/token", CONTENT_TYPE, (req, res) -> {
            String authHeader = req.headers("Authorization");
            String token = authHeader.split("Bearer ")[1];

            //JWT jwt = JwtUtil.verify(token);
            return new Response(true, token);
        }, new JsonTransformer());

    }
}

