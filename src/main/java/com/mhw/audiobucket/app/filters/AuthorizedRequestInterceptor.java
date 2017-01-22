package com.mhw.audiobucket.app.filters;

import com.auth0.jwt.JWT;
import com.mhw.audiobucket.security.JwtUtil;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * Created by michaelwomack on 1/22/17.
 */
public class AuthorizedRequestInterceptor implements Filter {

    public static final Logger LOGGER = Logger.getLogger(AuthorizedRequestInterceptor.class.getName());

    @Override
    public void handle(Request request, Response response) throws Exception {
        String authHeader = request.headers("Authorization");
        if (authHeader != null) {
            try {
                String jwtStr = authHeader.split("Bearer ")[1];
                JWT jwt = JwtUtil.verify(jwtStr);
                response.cookie("identity", jwt.getSubject());
                LOGGER.log(Level.INFO, jwtStr);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error decoding token from Authorization header: " + authHeader, e);
                halt(401, "No authentication token.");
            }
        } else {
            String message = "No Authorization header to check.";
            halt(401, message);
            LOGGER.log(Level.INFO, message);
        }
    }
}
