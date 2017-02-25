package com.mhw.audiobucket.model;

/**
 * Created by mxw4182 on 12/16/16.
 */
public class Artist {

    private long id;
    private String name;
    private String bio;
    private String pageUrl;

    public Artist() {}

    public Artist(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public Artist(long id, String name, String bio) {
        this(name, bio);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                '}';
    }
}
