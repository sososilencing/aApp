package com.example.novel.model;

import android.view.View;

public class User {
    private int Id;
    private String Email;
    private String Sex;
    private int Age;
    private String Nickname;
    private String Password;

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        this.Nickname = nickname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + Id +
                ", email='" + Email + '\'' +
                ", nickname='" + Nickname + '\'' +
                ", password='" + Password + '\'' +
                '}';
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
