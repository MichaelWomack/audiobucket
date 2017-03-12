package com.mhw.audiobucket.model;

import java.util.Date;

/**
 * Created by mxw4182 on 12/28/16.
 */
public class User {

    private long id;
    private String email;
    private String password;
    private Date dateCreated;
    private boolean isActive;
    private long artistId;

    public User() {

    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isActive != user.isActive) return false;
        if (artistId != user.artistId) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        return dateCreated != null ? dateCreated.equals(user.dateCreated) : user.dateCreated == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (int) (artistId ^ (artistId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated +
                ", isActive=" + isActive +
                ", artistId=" + artistId +
                '}';
    }
}
