package com.example.novel.model;

public class TokenUser {
    private String Token;
    private  User User;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }
}
