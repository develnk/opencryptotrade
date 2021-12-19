package com.opencryptotrade.commons.user.dto;

public class User {

    private Long id;

    private String username;

    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
