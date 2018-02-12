package com.dhavalanjaria.dyerest.models;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public class User {

    private String userId;
    private String username;

    public User() {
        // For DataSnapshot.getValue()
    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
