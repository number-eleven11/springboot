package com.example.demo.provider.User;

import lombok.Data;

@Data
public class GithubUser {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    private String login;
    private Long id;
    private String bio;
    private String avatar_url;

    @Override
    public String toString() {
        return "GithubUser{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
