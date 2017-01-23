package com.mhw.audiobucket.model;

/**
 * Created by mxw4182 on 12/16/16.
 */
public class Response {

    private boolean success;
    private Object data;
    private String message;
    private String token;
    private User user;
    private Artist artist;


    public Response() {}

    private Response(boolean success) {
        this.success = success;
    }

    public Response(boolean success, Object data) {
        this(success);
        this.data = data;
    }

    public Response(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public Response(boolean success, String message, String token) {
        this(success, message);
        this.token = token;
    }

    public Response(boolean success, User user) {
        this(success);
        this.user = user;
    }

    public Response(boolean success, Artist artist) {
        this(success);
        this.artist = artist;
    }
}
