package com.sourcey.materiallogindemo;

public class User {

    private String username;
    private String password;
    private String bio;

    public User() {
    }

    public User(String un, String pw, String b) {
        this.username = un;
        this.password = pw;
        this.bio = b;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }
    public void setUsername(String un){
        this.username = un;
    }
    public void setBio(String b) {
        this.bio = b;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getBio() {
        return bio;
    }

}