package com.mhw.audiobucket.model;

/**
 * Created by mxw4182 on 12/16/16.
 */
public class Artist {

    private long id;
    private String name;
    private String bio;

    public Artist(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public Artist(long id, String name, String bio) {
        this(name, bio);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public long getId() {
        return id;
    }
}
